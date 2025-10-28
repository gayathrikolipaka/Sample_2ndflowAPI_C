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

// Additional imports for new functionality
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

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

    // =========================
    // New fields for invalid JSON scenario
    // =========================
    String invalidJsonPayload;
    Response invalidJsonResponse;

    @Given("I have an invalid JSON payload for user creation")
    public void i_have_an_invalid_json_payload_for_user_creation() {
        // Load invalid JSON from test data file
        try {
            String testDataPath = "src/test/resources/testdata/invalid-json-format-data.json";
            String fileContent = new String(Files.readAllBytes(Paths.get(testDataPath)));
            // For this test, let's assume the invalid JSON is the first object's 'invalidPayload' field
            // e.g., [{..., "invalidPayload": "{\"userName\": \"test, \"password\": \"1234\"}"}]
            // We'll extract it manually for now (ideally, parse JSON, but keep it simple here)
            int idx = fileContent.indexOf("invalidPayload");
            if (idx >= 0) {
                int start = fileContent.indexOf('"', idx + 16) + 1;
                int end = fileContent.indexOf('"', start);
                invalidJsonPayload = fileContent.substring(start, end).replace("\\\"", "\"");
            } else {
                // Fallback to a known invalid JSON string
                invalidJsonPayload = "{\"userName\": \"test, \"password\": \"1234\"}"; // missing closing quote for userName
            }
        } catch (IOException e) {
            // Fallback to a known invalid JSON string
            invalidJsonPayload = "{\"userName\": \"test, \"password\": \"1234\"}";
        }
    }

    @When("I send POST request to create user with invalid JSON")
    public void i_send_post_request_to_create_user_with_invalid_json() {
        String baseUri = ConfigReader.getProperty("baseUri");
        RequestSpecification request = RestAssured.given()
                .baseUri(baseUri)
                .basePath("/Account/v1/User")
                .header("Content-Type", "application/json")
                .body(invalidJsonPayload);
        invalidJsonResponse = request.post();
    }

    @Then("I should get valid response with status code \"400\"")
    public void i_should_get_valid_response_with_status_code_400() {
        System.out.println("Response: " + invalidJsonResponse.asPrettyString());
        Assert.assertEquals(invalidJsonResponse.getStatusCode(), 400, "Expected 400 Bad Request for invalid JSON");
    }

    @And("the response should indicate a bad request due to invalid JSON format")
    public void the_response_should_indicate_a_bad_request_due_to_invalid_json_format() {
        String responseBody = invalidJsonResponse.asString();
        // Check for typical error message or indication of invalid JSON
        boolean hasInvalidJsonMsg = responseBody.toLowerCase().contains("invalid") ||
                                    responseBody.toLowerCase().contains("json") ||
                                    responseBody.toLowerCase().contains("bad request");
        Assert.assertTrue(hasInvalidJsonMsg, "Response should indicate invalid JSON format or bad request. Actual: " + responseBody);
    }
}