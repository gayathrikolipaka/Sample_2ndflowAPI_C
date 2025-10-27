package com.api.base;

import com.api.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseTest {

    private static RequestSpecification requestSpec;

    // Singleton pattern: only create it once
    public static RequestSpecification requestSpec() {
        if (requestSpec == null) {
            requestSpec = new RequestSpecBuilder()
                    .setBaseUri(ConfigReader.getProperty("baseURI"))
                    .addHeader("x-api-key", ConfigReader.getProperty("apiKey")) // replace header key as per your API
                    .setContentType(ContentType.JSON)
                    .build();
        }
        System.out.println("requestSpec "+requestSpec);
        return requestSpec;
    }
}
