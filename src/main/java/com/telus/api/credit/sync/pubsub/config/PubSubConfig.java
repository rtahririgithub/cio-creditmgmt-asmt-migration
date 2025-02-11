package com.telus.api.credit.sync.pubsub.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class PubSubConfig {

    public static final String XCONV_SUBSCRIPTION_NAME_PROPERTY_KEY = "${start.asmt.migration.pubsub.subscriptionName}";

    private static final Log LOGGER = LogFactory.getLog(PubSubConfig.class);

    @Bean
    public MessageChannel xConvPubSubInputChannel() {
        return new PublishSubscribeChannel();
    }

    @Bean
    @ConditionalOnProperty("start.asmt.migration.pubsub.subscriptionName")
    public PubSubInboundChannelAdapter xConvMessageChannelAdapter(PubSubTemplate pubSubTemplate, @Value(XCONV_SUBSCRIPTION_NAME_PROPERTY_KEY) String subscriptionName) {
        PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, subscriptionName);
        adapter.setOutputChannel(xConvPubSubInputChannel());
        adapter.setAckMode(AckMode.MANUAL);
        adapter.setPayloadType(String.class);
        LOGGER.info("Asmt Data migration pubsub init");
        return adapter;
    }
}
