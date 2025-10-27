package com.api.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Generic JSON payload builder for Create/Update/Delete/Get/Patch.
 */
public class PayloadManager {
	
	   /** Build a JSON *String* payload for creating or updating a user (backwards compatible). */
    public static String createUserPayload(String name, String job) {
        return createUserObject(name, job).toString();
    }
    
    /** Build a JSON *object* payload for creating or updating a user. */
    public static JSONObject createUserObject(String name, String job) {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("job", job);
        return obj;
    }

    public static JSONObject buildPayload(Map<String, Object> data) {
        JSONObject json = new JSONObject();
        if (data != null) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
        }
        return json;
    }

    public static JSONObject buildPayload(Object... keyValues) {
        if (keyValues == null || keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid args. Use key,value pairs.");
        }
        JSONObject json = new JSONObject();
        for (int i = 0; i < keyValues.length; i += 2) {
            json.put(String.valueOf(keyValues[i]), keyValues[i + 1]);
        }
        return json;
    }

    public static JSONObject deletePayload(String key, Object value) {
        JSONObject json = new JSONObject();
        if (value instanceof List) {
            json.put(key, new JSONArray((List<?>) value));
        } else {
            json.put(key, value);
        }
        return json;
    }

    public static JSONObject getPayload(Map<String, Object> filters) {
        return buildPayload(filters);
    }

    public static JSONObject updateFieldPayload(String key, Object value) {
        return buildPayload(key, value);
    }

    public static JSONObject buildNestedPayload(Map<String, Object> data) {
        return buildPayload(data);
    }

    public static JSONObject buildArrayPayload(String key, List<?> elements) {
        JSONObject json = new JSONObject();
        json.put(key, new JSONArray(elements));
        return json;
    }
    /** Build a payload for partial updates (PATCH-like). */
    public static String updateFieldPayload(String key, String value) {
        JSONObject payload = new JSONObject();
        payload.put(key, value);
        return payload.toString();
    }
}
