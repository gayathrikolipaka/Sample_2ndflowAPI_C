package com.api.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static JsonPath jsonPath(Response response) {
        return response.jsonPath();
    }

    public static String getString(Response response, String path) {
        return jsonPath(response).getString(path);
    }

    public static Integer getInt(Response response, String path) {
        return jsonPath(response).getInt(path);
    }

    public static Boolean getBoolean(Response response, String path) {
        return jsonPath(response).getBoolean(path);
    }

    public static <T> List<T> getList(Response response, String path) {
        return jsonPath(response).getList(path);
    }

    public static Map<String, Object> getMap(Response response, String path) {
        return jsonPath(response).getMap(path);
    }
}
