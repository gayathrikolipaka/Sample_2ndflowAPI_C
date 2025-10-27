package com.api.stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;

public class HandleBadRequestSteps {

    private String malformedPayload;
    private Response response;
    private final String BASE_URI = "https://bookstore.toolsqa.com"; // Ideally from config
    private final String ENDPOINT = "/Account/v1/Authorized";

    @Given("I have a malformed request payload for account authorization")
    public void i_have_a_malformed_request_payload_for_account_authorization() {
        // Load malformed payload from test data file
        try {
            String testDataPath = "src/test/resources/testdata/handle-bad-request-data.json";
            String content = new String(Files.readAllBytes(Paths.get(testDataPath)));
            JSONArray arr = new JSONArray(content);
            // Assume first object is the malformed payload scenario
            JSONObject obj = arr.getJSONObject(0);
            if (obj.has("malformedPayload")) {
                malformedPayload = obj.getString("malformedPayload");
            } else if (obj.has("payload")) {
                malformedPayload = obj.getString("payload");
            } else {
                // Fallback: use a generic malformed JSON
                malformedPayload = "{\"usernam\": \"test\""; // missing closing brace and misspelled key
            }
        } catch (IOException e) {
            // Fallback: use a generic malformed JSON if file not found
            malformedPayload = "{\"usernam\": \"test\"";
        }
    }

    @When("I send POST request to /Account/v1/Authorized")
    public void i_send_post_request_to_account_v1_authorized() {
        RequestSpecification request = RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(ENDPOINT)
                .header("Content-Type", "application/json")
                .body(malformedPayload);
        response = request.post();
    }

    @Then("I should get valid response with status code {string}")
    public void i_should_get_valid_response_with_status_code(String statusCode) {
        Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode),
                "Expected status code " + statusCode + " but got " + response.getStatusCode());
    }

    @And("the response should contain error message indicating malformed request syntax")
    public void the_response_should_contain_error_message_indicating_malformed_request_syntax() {
        String responseBody = response.asString();
        // Typical error messages for malformed JSON
        boolean hasMalformedMsg = responseBody.toLowerCase().contains("malformed") ||
                                  responseBody.toLowerCase().contains("invalid") ||
                                  responseBody.toLowerCase().contains("syntax");
        Assert.assertTrue(hasMalformedMsg,
                "Response does not contain error message for malformed request. Actual: " + responseBody);
    }
}
