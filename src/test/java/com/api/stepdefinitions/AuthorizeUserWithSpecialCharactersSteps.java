package com.api.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import com.api.utils.ConfigReader;

public class AuthorizeUserWithSpecialCharactersSteps {

    private String username;
    private String password;
    private Response response;
    private RequestSpecification request;

    @Given("I have user credentials with username {string} and password {string}")
    public void i_have_user_credentials_with_username_and_password(String username, String password) {
        this.username = username;
        this.password = password;
        // Prepare request spec with base URI from config
        String baseUri = ConfigReader.getProperty("baseUri");
        request = RestAssured.given().baseUri(baseUri)
                .header("Content-Type", "application/json");
    }

    @When("I send POST request to /Account/v1/Authorized endpoint")
    public void i_send_post_request_to_account_v1_authorized_endpoint() {
        // Build JSON payload
        Map<String, String> payload = new HashMap<>();
        payload.put("userName", username);
        payload.put("password", password);
        response = request.body(new JSONObject(payload).toString())
                .when()
                .post("/Account/v1/Authorized");
    }

    @Then("I should get valid response with status code {string}")
    public void i_should_get_valid_response_with_status_code(String statusCode) {
        Assert.assertEquals(String.valueOf(response.getStatusCode()), statusCode, "Status code mismatch");
    }

    @And("the response body should indicate successful authorization")
    public void the_response_body_should_indicate_successful_authorization() {
        // According to API, success is either empty body or a boolean true (or message)
        String responseBody = response.getBody().asString().trim();
        // Accept empty body or true or success message
        boolean isSuccess = responseBody.isEmpty() || responseBody.equalsIgnoreCase("true") || responseBody.toLowerCase().contains("success");
        Assert.assertTrue(isSuccess, "Response body does not indicate successful authorization. Actual: " + responseBody);
    }
}
