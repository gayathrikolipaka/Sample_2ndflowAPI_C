package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizeUserResponse {
    // The API returns 200 OK with an empty body or a success message.
    // If the body is empty, this class can remain empty.
    // If a success message is expected, add a field accordingly.
    // For now, we leave it empty as per the test case description.
}
