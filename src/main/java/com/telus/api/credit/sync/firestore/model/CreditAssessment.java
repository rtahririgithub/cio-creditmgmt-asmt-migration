
package com.telus.api.credit.sync.firestore.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.cloud.firestore.annotation.DocumentId;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "customerId",
        "creditProfileId",
        "creditAssessmentId",
        "creditAssessmentTimestamp",
        "creditAssessmentTypeCd",
        "creditAssessmentSubTypeCd",
        "creditAssessmentResultCd",
        "creditAssessmentResultReasonCd",
        "assessmentMessageCd",
        "createdBy",
        "createdTimestamp",
        "updatedBy",
        "updatedTimestamp",
        "originatorAppId",
        "channelOrgId"
})
public class CreditAssessment {

    @DocumentId
    @JsonProperty("id")
    private String id;
    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("creditProfileId")
    private String creditProfileId;
    @JsonProperty("creditAssessmentId")
    private String creditAssessmentId;
    @JsonProperty("creditAssessmentTimestamp")
    private String creditAssessmentTimestamp;
    @JsonProperty("creditAssessmentTypeCd")
    private String creditAssessmentTypeCd;
    @JsonProperty("creditAssessmentSubTypeCd")
    private String creditAssessmentSubTypeCd;
    @JsonProperty("creditAssessmentResultCd")
    private String creditAssessmentResultCd;
    @JsonProperty("creditAssessmentResultReasonCd")
    private String creditAssessmentResultReasonCd;
    @JsonProperty("assessmentMessageCd")
    private String assessmentMessageCd;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdTimestamp")
    private String createdTimestamp;
    @JsonProperty("updatedBy")
    private String updatedBy;
    @JsonProperty("updatedTimestamp")
    private String updatedTimestamp;
    @JsonProperty("originatorAppId")
    private String originatorAppId;
    @JsonProperty("channelOrgId")
    private String channelOrgId;

    /**
     * No args constructor for use in serialization
     */
    public CreditAssessment() {
    }

    /**
     * @param creditAssessmentResultCd
     * @param updatedBy
     * @param creditAssessmentTimestamp
     * @param creditAssessmentId
     * @param creditProfileId
     * @param createdTimestamp
     * @param assessmentMessageCd
     * @param updatedTimestamp
     * @param creditAssessmentSubTypeCd
     * @param createdBy
     * @param originatorAppId
     * @param customerId
     * @param creditAssessmentResultReasonCd
     * @param creditAssessmentTypeCd
     * @param channelOrgId
     */
    public CreditAssessment(String customerId, String creditProfileId, String creditAssessmentId, String creditAssessmentTimestamp, String creditAssessmentTypeCd, String creditAssessmentSubTypeCd, String creditAssessmentResultCd, String creditAssessmentResultReasonCd, String assessmentMessageCd, String createdBy, String createdTimestamp, String updatedBy, String updatedTimestamp, String originatorAppId, String channelOrgId) {
        super();
        this.customerId = customerId;
        this.creditProfileId = creditProfileId;
        this.creditAssessmentId = creditAssessmentId;
        this.creditAssessmentTimestamp = creditAssessmentTimestamp;
        this.creditAssessmentTypeCd = creditAssessmentTypeCd;
        this.creditAssessmentSubTypeCd = creditAssessmentSubTypeCd;
        this.creditAssessmentResultCd = creditAssessmentResultCd;
        this.creditAssessmentResultReasonCd = creditAssessmentResultReasonCd;
        this.assessmentMessageCd = assessmentMessageCd;
        this.createdBy = createdBy;
        this.createdTimestamp = createdTimestamp;
        this.updatedBy = updatedBy;
        this.updatedTimestamp = updatedTimestamp;
        this.originatorAppId = originatorAppId;
        this.channelOrgId = channelOrgId;
    }

    @JsonProperty("id")
    public String getId() { return id; }

    @JsonProperty("id")
    public void setId(String id) { this.id = id; }

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    @JsonProperty("customerId")
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("creditProfileId")
    public String getCreditProfileId() {
        return creditProfileId;
    }

    @JsonProperty("creditProfileId")
    public void setCreditProfileId(String creditProfileId) {
        this.creditProfileId = creditProfileId;
    }

    @JsonProperty("creditAssessmentId")
    public String getCreditAssessmentId() {
        return creditAssessmentId;
    }

    @JsonProperty("creditAssessmentId")
    public void setCreditAssessmentId(String creditAssessmentId) {
        this.creditAssessmentId = creditAssessmentId;
    }

    @JsonProperty("creditAssessmentTimestamp")
    public String getCreditAssessmentTimestamp() {
        return creditAssessmentTimestamp;
    }

    @JsonProperty("creditAssessmentTimestamp")
    public void setCreditAssessmentTimestamp(String creditAssessmentTimestamp) {
        this.creditAssessmentTimestamp = creditAssessmentTimestamp;
    }

    @JsonProperty("creditAssessmentTypeCd")
    public String getCreditAssessmentTypeCd() {
        return creditAssessmentTypeCd;
    }

    @JsonProperty("creditAssessmentTypeCd")
    public void setCreditAssessmentTypeCd(String creditAssessmentTypeCd) {
        this.creditAssessmentTypeCd = creditAssessmentTypeCd;
    }

    @JsonProperty("creditAssessmentSubTypeCd")
    public String getCreditAssessmentSubTypeCd() {
        return creditAssessmentSubTypeCd;
    }

    @JsonProperty("creditAssessmentSubTypeCd")
    public void setCreditAssessmentSubTypeCd(String creditAssessmentSubTypeCd) {
        this.creditAssessmentSubTypeCd = creditAssessmentSubTypeCd;
    }

    @JsonProperty("creditAssessmentResultCd")
    public String getCreditAssessmentResultCd() {
        return creditAssessmentResultCd;
    }

    @JsonProperty("creditAssessmentResultCd")
    public void setCreditAssessmentResultCd(String creditAssessmentResultCd) {
        this.creditAssessmentResultCd = creditAssessmentResultCd;
    }

    @JsonProperty("creditAssessmentResultReasonCd")
    public String getCreditAssessmentResultReasonCd() {
        return creditAssessmentResultReasonCd;
    }

    @JsonProperty("creditAssessmentResultReasonCd")
    public void setCreditAssessmentResultReasonCd(String creditAssessmentResultReasonCd) {
        this.creditAssessmentResultReasonCd = creditAssessmentResultReasonCd;
    }

    @JsonProperty("assessmentMessageCd")
    public String getAssessmentMessageCd() {
        return assessmentMessageCd;
    }

    @JsonProperty("assessmentMessageCd")
    public void setAssessmentMessageCd(String assessmentMessageCd) {
        this.assessmentMessageCd = assessmentMessageCd;
    }

    @JsonProperty("createdBy")
    public String getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("createdTimestamp")
    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    @JsonProperty("createdTimestamp")
    public void setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @JsonProperty("updatedBy")
    public String getUpdatedBy() {
        return updatedBy;
    }

    @JsonProperty("updatedBy")
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonProperty("updatedTimestamp")
    public String getUpdatedTimestamp() {
        return updatedTimestamp;
    }

    @JsonProperty("updatedTimestamp")
    public void setUpdatedTimestamp(String updatedTimestamp) {
        this.updatedTimestamp = updatedTimestamp;
    }

    @JsonProperty("originatorAppId")
    public String getOriginatorAppId() {
        return originatorAppId;
    }

    @JsonProperty("originatorAppId")
    public void setOriginatorAppId(String originatorAppId) {
        this.originatorAppId = originatorAppId;
    }

    @JsonProperty("channelOrgId")
    public String getChannelOrgId() {
        return channelOrgId;
    }

    @JsonProperty("channelOrgId")
    public void setChannelOrgId(String channelOrgId) {
        this.channelOrgId = channelOrgId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("creditProfileId", creditProfileId).append("creditAssessmentId", creditAssessmentId).append("creditAssessmentTimestamp", creditAssessmentTimestamp).append("creditAssessmentTypeCd", creditAssessmentTypeCd).append("creditAssessmentSubTypeCd", creditAssessmentSubTypeCd).append("creditAssessmentResultCd", creditAssessmentResultCd).append("creditAssessmentResultReasonCd", creditAssessmentResultReasonCd).append("assessmentMessageCd", assessmentMessageCd).append("createdBy", createdBy).append("createdTimestamp", createdTimestamp).append("updatedBy", updatedBy).append("updatedTimestamp", updatedTimestamp).append("originatorAppId", originatorAppId).append("channelOrgId", channelOrgId).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CreditAssessment that = (CreditAssessment) o;

        return new EqualsBuilder().append(customerId, that.customerId).append(creditProfileId, that.creditProfileId).append(creditAssessmentId, that.creditAssessmentId).append(creditAssessmentTimestamp, that.creditAssessmentTimestamp).append(creditAssessmentTypeCd, that.creditAssessmentTypeCd).append(creditAssessmentSubTypeCd, that.creditAssessmentSubTypeCd).append(creditAssessmentResultCd, that.creditAssessmentResultCd).append(creditAssessmentResultReasonCd, that.creditAssessmentResultReasonCd).append(assessmentMessageCd, that.assessmentMessageCd).append(createdBy, that.createdBy).append(createdTimestamp, that.createdTimestamp).append(updatedBy, that.updatedBy).append(updatedTimestamp, that.updatedTimestamp).append(originatorAppId, that.originatorAppId).append(channelOrgId, that.channelOrgId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(customerId).append(creditProfileId).append(creditAssessmentId).append(creditAssessmentTimestamp).append(creditAssessmentTypeCd).append(creditAssessmentSubTypeCd).append(creditAssessmentResultCd).append(creditAssessmentResultReasonCd).append(assessmentMessageCd).append(createdBy).append(createdTimestamp).append(updatedBy).append(updatedTimestamp).append(originatorAppId).append(channelOrgId).toHashCode();
    }
}
