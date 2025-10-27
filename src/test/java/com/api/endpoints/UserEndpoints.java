package com.api.endpoints;

import com.api.base.BaseTest;
import com.api.utils.ConfigReader;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserEndpoints {

    public Response createUser(String payload) {
        return given().spec(BaseTest.requestSpec())
                .body(payload)
                .when().post(ConfigReader.getProperty("createUserEndpoint"));
    }

    public Response getUserById(String userId) {
        return given().spec(BaseTest.requestSpec())
                .pathParam("id", userId)
                .when().get(ConfigReader.getProperty("getUserEndpoint"));
    }

    public Response getWithQuery(Map<String, Object> queryParams) {
        return given().spec(BaseTest.requestSpec())
                .queryParams(queryParams)
                .when().get(ConfigReader.getProperty("getUserWithQueryEndpoint"));
    }

    public Response getAllUsers(int page) {
        return given().spec(BaseTest.requestSpec())
                .queryParam("page", page)
                .when().get(ConfigReader.getProperty("getAllUsersEndpoint"));
    }

    public Response updateUser(String userId, String payload) {
        return given().spec(BaseTest.requestSpec())
                .pathParam("id", userId)
                .body(payload)
                .when().put(ConfigReader.getProperty("updateUserEndpoint"));
    }

    public Response deleteUserById(String userId) {
        return given().spec(BaseTest.requestSpec())
                .pathParam("id", userId)
                .when().delete(ConfigReader.getProperty("deleteUserEndpoint"));
    }

    public Response deleteWithBody(String payload) {
        return given().spec(BaseTest.requestSpec())
                .body(payload)
                .when().delete(ConfigReader.getProperty("deleteUserEndpoint"));
    }
    public Response getUsersWithQueryParam(String key, String value) {
		return given().spec(BaseTest.requestSpec()).queryParam(key, value).when()
				.get(ConfigReader.getProperty("getUserWithQueryEndpoint")); // e.g. /users
	}
    public Response getAllUsers() {
		return given().spec(BaseTest.requestSpec()).when().get(ConfigReader.getProperty("getAllUsersEndpoint")); // e.g.
																													// /users
	}
}
