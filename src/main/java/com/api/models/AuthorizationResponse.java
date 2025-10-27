package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * POJO for /Account/v1/Authorized POST response payload.
 * According to the test case, the response is 200 OK with an empty body or a success message.
 * This class is prepared for both possibilities.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationResponse {
    // If the response is empty, this class remains empty.
    // If a success message is returned, add the field below:

    // @JsonProperty("message")
    // private String message;
    // // TODO: Uncomment and use if API returns a message field.

    // public String getMessage() {
    //     return message;
    // }

    // public void setMessage(String message) {
    //     this.message = message;
    // }
}
