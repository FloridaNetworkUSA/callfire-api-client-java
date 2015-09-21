package com.callfire.api.client;

import com.callfire.api.client.model.Page;
import com.callfire.api.client.model.ResourceId;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.InputStream;

/**
 * Client constants
 */
public interface ClientConstants {
    String BASE_PATH = "https://www-stg4.callfire.com/api/v2";
    //String BASE_PATH = "https://www.callfire.com/api/v2";
    String PLACEHOLDER = "\\{\\}";
    // Use ISO 8601 format for date and datetime.
    // See https://en.wikipedia.org/wiki/ISO_8601
    String DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    // TODO vmikhailov add versioning to user agent
    String USER_AGENT = "Java-Client-0.1-SNAPSHOT";

    /**
     * Jackson TypeReference types for valid serialization/deserialization
     */
    interface Type {
        TypeReference<String> STRING_TYPE = new TypeReference<String>() {
        };
        TypeReference<Void> VOID_TYPE = new TypeReference<Void>() {
        };
        TypeReference<Boolean> BOOLEAN_TYPE = new TypeReference<Boolean>() {
        };
        TypeReference<InputStream> INPUT_STREAM_TYPE = new TypeReference<InputStream>() {
        };
        TypeReference<ResourceId> RESOURCE_ID_TYPE = new TypeReference<ResourceId>() {
        };
        TypeReference<Page<ResourceId>> PAGE_OF_RESOURCE_ID_TYPE = new TypeReference<Page<ResourceId>>() {
        };
    }
}
