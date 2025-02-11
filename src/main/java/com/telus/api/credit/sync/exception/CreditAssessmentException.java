package com.telus.api.credit.sync.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class CreditAssessmentException extends Exception {

    private String code;
    private String reason;
    private String serviceMessage;

    public CreditAssessmentException(String code, String reason, String message) {
        super(message);
        this.code = code;
        this.reason = reason;
        this.serviceMessage = message;
    }

    public CreditAssessmentException(String code, String reason, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.reason = reason;
        this.serviceMessage = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(String serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    @Override
    public String toString() {
        return "PubSubException{" +
                "code='" + code + '\'' +
                ", reason='" + reason + '\'' +
                ", serviceMessage='" + serviceMessage + '\'' +
                '}';
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>(3);
        map.put("code", code);
        map.put("reason", reason);
        map.put("message", serviceMessage);
        return map;
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(toMap());
    }
}
