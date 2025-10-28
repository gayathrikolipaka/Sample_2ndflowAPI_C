package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for error response payload when invalid JSON format is sent to /Account/v1/User
 * Expected for 400 Bad Request scenarios.
 */
public class ErrorResponse {

    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
