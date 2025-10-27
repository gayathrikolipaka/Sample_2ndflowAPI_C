package com.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationRequest {

    @JsonProperty("userName")
    private Object userName; // Accepts any type for negative testing

    @JsonProperty("password")
    private Object password; // Accepts any type for negative testing

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(Object userName, Object password) {
        this.userName = userName;
        this.password = password;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }
}
