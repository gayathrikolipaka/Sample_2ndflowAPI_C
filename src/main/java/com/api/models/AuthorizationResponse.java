package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationResponse {
    // The API is expected to return an empty body or a success message on 200 OK.
    // If the response is empty, this class remains empty.
    // If a success message is returned, add a field as below:
    // @JsonProperty("message")
    // private String message;
    //
    // public String getMessage() { return message; }
    // public void setMessage(String message) { this.message = message; }
}
