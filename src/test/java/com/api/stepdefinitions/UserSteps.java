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

    JSONObject loginPayload;

    @Given("I have valid user credentials with username {string} and password {string}")
    public void i_have_valid_user_credentials_with_username_and_password(String username, String password) {
        // Store credentials for use in the request
        // You can extend this to use a POJO if needed
        this.loginPayload = new org.json.JSONObject();
        loginPayload.put("userName", username);
        loginPayload.put("password", password);
    }

    @When("I send POST request to login endpoint")
    public void i_send_post_request_to_login_endpoint() {
        // Use ConfigReader to get base URI and endpoint path
        String baseUri = ConfigReader.getProperty("baseUri");
        String endpoint = "/Account/v1/Authorized";
        
        response = io.restassured.RestAssured.given()
                .baseUri(baseUri)
                .header("Content-Type", "application/json")
                .body(loginPayload.toString())
                .when()
                .post(endpoint);
    }

    @Then("I should get valid response with status code {string}")
    public void i_should_get_valid_response_with_status_code_login(String statusCode) {
        System.out.println("Login Response: " + response.asPrettyString());
        System.out.println("Status Code: " + response.getStatusCode());
        org.testng.Assert.assertEquals(String.valueOf(response.getStatusCode()), statusCode, "Status code mismatch");
    }

    @And("the response body should indicate successful authentication")
    public void the_response_body_should_indicate_successful_authentication() {
        // For /Account/v1/Authorized, API may return true/false or empty body for 200 OK
        String responseBody = response.getBody().asString().trim();
        // Acceptable: empty body or 'true' (depending on API contract)
        boolean isSuccess = responseBody.isEmpty() || responseBody.equalsIgnoreCase("true");
        org.testng.Assert.assertTrue(isSuccess, "Authentication not successful. Body: " + responseBody);
    }
}