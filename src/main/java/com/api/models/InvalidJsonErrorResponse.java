package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing the error response for invalid JSON format (HTTP 400 Bad Request).
 * This class can be used to deserialize error responses when invalid JSON is sent to the API.
 */
public class InvalidJsonErrorResponse {

    @JsonProperty("code")
    private String code; // e.g., "400"

    @JsonProperty("message")
    private String message; // e.g., "Invalid JSON format."

    // TODO: Replace with actual field names if the API returns a different error schema

    public InvalidJsonErrorResponse() {
    }

    public InvalidJsonErrorResponse(String code, String message) {
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

    @Override
    public String toString() {
        return "InvalidJsonErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
