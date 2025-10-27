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

    // ===================== NEWLY ADDED METHODS BELOW =====================

    @Given("I have user credentials payload with username {string} and password {string}")
    public void i_have_user_credentials_payload_with_username_and_password(String username, String password) {
        // Build a JSON payload with potentially invalid datatypes
        // The username and password may be numbers, booleans, null, or strings (from Examples table)
        org.json.JSONObject payloadJson = new org.json.JSONObject();
        // Handle 'null' string as actual null
        if ("null".equals(username)) {
            payloadJson.put("userName", org.json.JSONObject.NULL);
        } else if (isBoolean(username)) {
            payloadJson.put("userName", Boolean.parseBoolean(username));
        } else if (isNumeric(username)) {
            payloadJson.put("userName", Integer.parseInt(username));
        } else {
            // Remove quotes if present (from Examples table)
            payloadJson.put("userName", stripQuotes(username));
        }
        if ("null".equals(password)) {
            payloadJson.put("password", org.json.JSONObject.NULL);
        } else if (isBoolean(password)) {
            payloadJson.put("password", Boolean.parseBoolean(password));
        } else if (isNumeric(password)) {
            payloadJson.put("password", Integer.parseInt(password));
        } else {
            payloadJson.put("password", stripQuotes(password));
        }
        this.deletePayload = payloadJson.toString(); // Reuse deletePayload as generic payload holder
    }

    @When("I send POST request to check user credentials")
    public void i_send_post_request_to_check_user_credentials() {
        // Use REST Assured directly since endpoint is /Account/v1/Authorized
        String baseUri = com.api.utils.ConfigReader.getProperty("baseUri");
        String endpoint = "/Account/v1/Authorized";
        response = io.restassured.RestAssured.given()
                .baseUri(baseUri)
                .header("Content-Type", "application/json")
                .body(deletePayload)
                .when()
                .post(endpoint);
    }

    @And("the response should contain error message indicating invalid data type")
    public void the_response_should_contain_error_message_indicating_invalid_data_type() {
        String responseBody = response.asString();
        // The actual error message may vary; check for common patterns
        boolean hasTypeError = responseBody.contains("type") ||
                               responseBody.toLowerCase().contains("invalid") ||
                               responseBody.toLowerCase().contains("data type") ||
                               responseBody.toLowerCase().contains("expected");
        org.testng.Assert.assertTrue(hasTypeError,
                "Expected error message about invalid data type, but got: " + responseBody);
    }

    // Helper methods for payload construction
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
    private boolean isBoolean(String str) {
        return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str);
    }
    private String stripQuotes(String str) {
        if (str == null) return null;
        if (str.startsWith("\"") && str.endsWith("\"")) {
            return str.substring(1, str.length() - 1);
        }
        return str;
    }
}