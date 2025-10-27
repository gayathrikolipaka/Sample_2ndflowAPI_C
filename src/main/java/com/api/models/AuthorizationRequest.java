package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for /Account/v1/Authorized POST request payload.
 * Represents the user credentials for authorization.
 */
public class AuthorizationRequest {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
