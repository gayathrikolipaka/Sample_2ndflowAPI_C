package com.api.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import com.api.utils.ConfigReader;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuthorizeUserSteps {

    private String username;
    private String password;
    private Response response;
    private RequestSpecification request;

    @Given("I have user credentials with username \"{string}\" and password \"{string}\"")
    public void i_have_user_credentials_with_username_and_password(String username, String password) {
        this.username = username;
        this.password = password;
        request = RestAssured.given()
                .baseUri(ConfigReader.getProperty("baseURI"))
                .basePath("/Account/v1/Authorized")
                .header("Content-Type", "application/json");
    }

    @When("I send POST request to authorize user")
    public void i_send_post_request_to_authorize_user() {
        JSONObject payload = new JSONObject();
        payload.put("userName", username);
        payload.put("password", password);
        response = request.body(payload.toString()).post();
    }

    @And("the response body should indicate authorization success")
    public void the_response_body_should_indicate_authorization_success() {
        // According to API, a successful authorization returns true or an empty body
        String body = response.getBody().asString();
        // Accept either empty body or a body with 'true' (boolean or string)
        boolean isSuccess = body.trim().isEmpty() || body.trim().equalsIgnoreCase("true") || body.trim().equalsIgnoreCase("{\"isAuthorized\":true}");
        Assert.assertTrue(isSuccess, "Authorization response body did not indicate success. Actual body: " + body);
    }
}
