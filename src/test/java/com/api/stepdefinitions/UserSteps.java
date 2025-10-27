package com.api.stepdefinitions;

import com.api.endpoints.UserEndpoints;
import com.api.utils.ConfigReader;
import com.api.utils.PayloadManager;
import com.api.utils.JsonUtils;
import org.json.JSONObject;
import java.util.Map;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

public class UserSteps {

    Response response;
    UserEndpoints endpoints = new UserEndpoints();    

    String userId;
    String queryParamKey;
    String queryParamValue;
    String deletePayload;
    String updatePayload;
    // Ideally, fetch this from config/properties file
//    private final String apiKey = ConfigReader.getProperty("apiKey");

    @Given("I have user details payload with name {string} and job {string}")
    public void i_have_user_details_payload_with_name_and_job(String name, String job) {
        // You can also externalize this payload to JSON files later
    	
    }
    
    @Given("I have user details payload")
    public void i_have_user_details_payload() {
        // You can also externalize this payload to JSON files later
    }
   
    @When("I send POST request to create user with name {string} and job {string}")
    public void i_send_post_request_to_create_user_with_name_and_job(String name, String job) {
        String payload = PayloadManager.createUserPayload(name, job);
        System.out.println("Payload: " + payload);

        response = endpoints.createUser(payload);
    }

   
    @Then("I should get valid response with status code {string}")
    public void i_should_get_valid_response_with_status_code(String statusCode) {
        System.out.println("Response: " + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.statusCode(), statusCode);
    }
    
    @Given("I have user ID {string}")
    public void i_have_user_id(String id) {
        this.userId = id;
    }

    @When("I send GET request to fetch user details by path param")
    public void i_send_get_request_to_fetch_user_details_by_path_param() {
        response = endpoints.getUserById(userId);
    }
    
    @Given("I have query parameter {string} with value {string}")
    public void i_have_query_parameter_with_value(String key, String value) {
        this.queryParamKey = key;
        this.queryParamValue = value;
    }

    @When("I send GET request to fetch users with query param")
    public void i_send_get_request_to_fetch_users_with_query_param() {
        response = endpoints.getUsersWithQueryParam(queryParamKey, queryParamValue);
    }
    
    @When("I send GET request to fetch all users")
    public void i_send_get_request_to_fetch_all_users() {
        response = endpoints.getAllUsers();
    }

    @Given("I have delete user payload with ID {string}")
    public void i_have_delete_user_payload_with_id(String userId) {
        deletePayload = String.format("{\"id\": \"%s\"}", userId);
    }

    @When("I send DELETE request with JSON body")
    public void i_send_delete_request_with_json_body() {
        response = endpoints.deleteWithBody(deletePayload);
    }

    @Given("I have update payload with name {string} and job {string}")
    public void i_have_update_payload_with_name_and_job(String name, String job) {
        updatePayload = PayloadManager.updateFieldPayload(name, job);
    }

    @When("I send PUT request to update user")
    public void i_send_put_request_to_update_user() {
        response = endpoints.updateUser(userId, updatePayload);
    }

    @Then("the delete response status should be {int}")
    public void the_delete_response_status_should_be(Integer statusCode) {
        Assert.assertEquals(response.getStatusCode(), (int) statusCode);
    }

    @Then("the response field {string} should equal {string}")
    public void the_response_field_should_equal(String path, String expected) {
        String actual = JsonUtils.getString(response, path);
        Assert.assertEquals(actual, expected, "Mismatch for path: " + path);
    }

    @Then("the list page should be {int}")
    public void the_list_page_should_be(Integer page) {
        Integer returned = JsonUtils.getInt(response, "page");
        Assert.assertEquals(returned, page);
    }

    // --- Supporting field for payload ---
    private String authPayload;

    // Step for: Given I have user credentials with username "<username>" and password "<password>"
    @Given("I have user credentials with username {string} and password {string}")
    public void i_have_user_credentials_with_username_and_password(String username, String password) {
        // Prepare the payload using a POJO or JSON object
        // Since no POJO exists, use JSONObject for now
        org.json.JSONObject payloadObj = new org.json.JSONObject();
        payloadObj.put("userName", username);
        payloadObj.put("password", password);
        // Store payload as a string for use in the When step
        this.authPayload = payloadObj.toString();
    }

    // Step for: When I send POST request to /Account/v1/Authorized endpoint
    @When("I send POST request to /Account/v1/Authorized endpoint")
    public void i_send_post_request_to_account_v1_authorized_endpoint() {
        // Use base URI from config if available, otherwise default
        String baseUri = com.api.utils.ConfigReader.getProperty("baseUri");
        if (baseUri == null || baseUri.isEmpty()) {
            baseUri = "https://demoqa.com"; // fallback default if not set
        }
        response = io.restassured.RestAssured.given()
                .baseUri(baseUri)
                .header("Content-Type", "application/json")
                .body(this.authPayload)
                .when()
                .post("/Account/v1/Authorized");
    }

    // Step for: And the response should indicate unauthorized access
    @And("the response should indicate unauthorized access")
    public void the_response_should_indicate_unauthorized_access() {
        // For 401 Unauthorized, the response body may be empty or contain a message
        // Assert status code is 401 (already checked in Then step, but double check for clarity)
        org.testng.Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 Unauthorized");
        // Optionally, check for error message in response body
        String responseBody = response.getBody().asString();
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            // Some APIs return a message field
            boolean containsUnauthorized = responseBody.toLowerCase().contains("unauthorized") ||
                                            responseBody.toLowerCase().contains("invalid") ||
                                            responseBody.toLowerCase().contains("not authorized");
            org.testng.Assert.assertTrue(containsUnauthorized,
                "Response body should indicate unauthorized access. Actual: " + responseBody);
        }
    }
}