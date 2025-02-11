package com.telus.api.credit.sync.pubsub.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telus.api.credit.sync.dao.XAsmtDao;
import com.telus.api.credit.sync.firestore.model.CreditAssessment;
import com.telus.api.credit.sync.pubsub.model.CreditAssessmentEvent;

@Service
public class XConvMessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(XConvMessageReceiver.class);

    private final PubSubTemplate pubSubTemplate;

    private final XAsmtDao xAsmtDao;

    private final JacksonPubSubMessageConverter messageConverter;

    private final String outputTopic;
    final static SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd'T'HH:mm:ss.sss'Z'");
    
    private int errCount;
    private int successCount;
    private boolean updateSyncStatus;
    private boolean doall;

    public XConvMessageReceiver(PubSubTemplate pubSubTemplate, 
                                XAsmtDao xSyncStatusDao, 
                                ObjectMapper objectMapper,
                                @Value("${target.pubsub.topicName:}") String outputTopic) {
        this.xAsmtDao = xSyncStatusDao;
        this.messageConverter = new JacksonPubSubMessageConverter(objectMapper);
        this.outputTopic = outputTopic;
        this.pubSubTemplate = pubSubTemplate;
    }

    @ServiceActivator(inputChannel = "xConvPubSubInputChannel")
    public void messageReceiver(@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
        try {
            LOGGER.info("Asmt Message received " + Instant.now() + " {}", message);
            ackMessage(message.ack(), message.getPubsubMessage().getMessageId());
            errCount = 0;
            successCount = 0;
            Instant start = Instant.now();
            
            this.updateSyncStatus = false;
            this.doall = false;
            String msg = message.getPubsubMessage().getData().toStringUtf8();
            if (msg != null) {
               msg = msg.toLowerCase();
               if (msg.contains("updatesync")) {
                  updateSyncStatus = true;
               }
               if (msg.contains("all")) {
                  doall = true;
               }
            }
            LOGGER.info("updateSyncStatus={} doall={}", updateSyncStatus, doall);
            
            processMessage(message);
            
            Instant end = Instant.now();
            long timeInMinutes = Duration.between(start, end).toMinutes();            
            LOGGER.info("Complete processing of assessments. " + end + " Took: " + timeInMinutes + " minutes");
            LOGGER.info("Successfully published: {} ", successCount);
            LOGGER.info("Error Count: " + errCount);
        } catch (Exception e) {
            LOGGER.error("Exception processing asmt pubsub message. messageId=" + message.getPubsubMessage().getMessageId(), e);
        }
    }

    private void processMessage(BasicAcknowledgeablePubsubMessage message) {
       long count = 0;
       String fromMsg = StringUtils.isNotBlank(message.getPubsubMessage().getData().toStringUtf8())? message.getPubsubMessage().getData().toStringUtf8().replaceAll("[^0-9]", ""): null;
       if(NumberUtils.isDigits(fromMsg)) {
          count = NumberUtils.toLong(fromMsg);
       } else {
          count = xAsmtDao.getCountMaxRecords();
       }
 
       long limit = 2000;
       LOGGER.info("Processing {} assessment records", count);
       
       if(!doall && count > limit) {
          long pages = count / limit;
          LOGGER.info("Batches {} of assessment records, records per page:{}", pages, limit);
          for(int i=0; i<= pages; i++) {
             //processCustomers(limit, i * limit);
             processCustomers(limit, 0);
             LOGGER.info("Batch No:{} processed", i+1 );
          }
          
       } else {
          processCustomers(count, 0);
       }  
     }
    
    private ObjectMapper mapper = new ObjectMapper();

   private void processCustomers(long limit, long offset) {
      List<Map<String, Object>> asmtTable = xAsmtDao.getAllNeedToSync(limit, offset);
      List<Long> custIds = new ArrayList<>();
      
      for (Map<String, Object> asmtRow : asmtTable) {
         String custId = getLongValueAsString(asmtRow.get("customer_id"));
         try {
            Objects.requireNonNull(custId);
            
            // Publish assessment sync message
            CreditAssessmentEvent creditAssessmentEvent = createCreditAssessmentEvent(asmtRow);
            String event = mapper.writeValueAsString(creditAssessmentEvent);
            pubSubTemplate.publish(outputTopic, event);
            
            successCount++;
            custIds.add(Long.valueOf(custId));

            // If we doing all, to be safe, update one at a time
            if (doall && this.updateSyncStatus) {
               // Set need_to_sync = false;               
               // This is slow, doing one at a time
               xAsmtDao.updateNeedToSyncFalse(Long.valueOf(custId));               
            }
         } catch (Exception e) {
            errCount++;
            LOGGER.error("Error publishing {}", custId, e);
         }
      }

      // Batch update the need_to_sync for all customers processed
      // Updating the Postgres database is very slow, don't need it.
      if (!doall && this.updateSyncStatus && custIds.size() > 0) {
         xAsmtDao.batchUpdateNeedToSyncFalse(custIds);   
      }
   }


    private void ackMessage(ListenableFuture<Void> future, String messageId) {
        try {
            future.get();
        } catch (Exception e) {
            LOGGER.warn("Exception acknowledging pubsub message. messageId=" + messageId);
        }
    }
    
    private CreditAssessmentEvent createCreditAssessmentEvent(Map<String, Object> asmtRow) {
       CreditAssessmentEvent event = new CreditAssessmentEvent();
       CreditAssessment payload = new CreditAssessment();
       payload.setCustomerId(getLongValueAsString(asmtRow.get("customer_id")));
       payload.setCreditProfileId(getLongValueAsString(asmtRow.get("credit_profile_id")));
       payload.setAssessmentMessageCd(getStringValue(asmtRow.get("assessment_message_cd")));
       payload.setCreditAssessmentId(getStringValue(asmtRow.get("credit_assessment_id")));
       Object ts = asmtRow.get("credit_assessment_ts");
       if (ts != null) {
          Timestamp t = (Timestamp) ts;
          String formatterTs = sdf.format(t);
          payload.setCreditAssessmentTimestamp(formatterTs);
          // "creditAssessmentTimestamp":"2021-02-09T19:00:57.752844300Z",
       }
       
       payload.setCreditAssessmentTypeCd(getStringValue(asmtRow.get("credit_assessment_typ_cd")));
       payload.setCreditAssessmentSubTypeCd(getStringValue(asmtRow.get("credit_assessment_subtyp_cd")));
       payload.setCreditAssessmentResultCd(getStringValue(asmtRow.get("credit_assessment_result_cd")));
       payload.setCreditAssessmentResultReasonCd(getStringValue(asmtRow.get("credit_assessment_result_reason_cd")));

       // Not required as per Reza.
       //payload.setOriginatorAppId("");
       //payload.setChannelOrgId("");
       //payload.setCreatedBy("");
       //payload.setCreatedTimestamp(Instant.now().toString());
       //payload.setUpdatedBy("");
       //payload.setUpdatedTimestamp(Instant.now().toString());
       
       
       List<CreditAssessment> payloadList = new ArrayList<>(1);
       payloadList.add(payload);
       event.setEvent(payloadList);
       event.setEventId(UUID.randomUUID().toString());
       event.setEventType("assessmentCreate");
       //event.setEventTime(Instant.now().toString());
       event.setDescription("assessment migration");
       return event;
   }

    private String getStringValue(Object obj) {
       if (obj == null) {
          return null;
       } else {
          return (String) obj;
       }
    }
    
    private String getLongValueAsString(Object obj) {
       if (obj == null) {
          return null;
       } else {
          return String.valueOf(obj);
       }
    }

}
