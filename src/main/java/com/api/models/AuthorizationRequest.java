package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationRequest {

    @JsonProperty("userName")
    private String userName;

    // Password is intentionally omitted for negative test (missing password)
    // private String password;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // No password field or getter/setter, as per negative test scenario
}
