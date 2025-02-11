
package com.telus.api.credit.sync.pubsub.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.telus.api.credit.sync.firestore.model.CreditAssessment;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eventId",
        "eventType",
        "eventTime",
        "description",
        "event"
})
public class CreditAssessmentEvent {

    @JsonProperty("eventId")
    private String eventId;
    @JsonProperty("eventType")
    private String eventType;
   // @JsonProperty("eventTime")
   // private String eventTime;
    @JsonProperty("description")
    private String description;
    @JsonProperty("event")
    private List<CreditAssessment> event = null;

    /**
     * No args constructor for use in serialization
     */
    public CreditAssessmentEvent() {
    }

    /**
     * @param eventId
     * @param eventTime
     * @param description
     * @param eventType
     * @param event
     */
    public CreditAssessmentEvent(String eventId, String eventType, String eventTime, String description, List<CreditAssessment> event) {
        super();
        this.eventId = eventId;
        this.eventType = eventType;
        //this.eventTime = eventTime;
        this.description = description;
        this.event = event;
    }

    @JsonProperty("eventId")
    public String getEventId() {
        return eventId;
    }

    @JsonProperty("eventId")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @JsonProperty("eventType")
    public String getEventType() {
        return eventType;
    }

    @JsonProperty("eventType")
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

	/*
	 * @JsonProperty("eventTime") public String getEventTime() { return eventTime; }
	 * 
	 * @JsonProperty("eventTime") public void setEventTime(String eventTime) {
	 * this.eventTime = eventTime; }
	 */

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("event")
    public List<CreditAssessment> getEvent() {
        return event;
    }

    @JsonProperty("event")
    public void setEvent(List<CreditAssessment> event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eventId", eventId).append("eventType", eventType)
        		//.append("eventTime", eventTime)
        		.append("description", description).append("event", event).toString();
    }

}
