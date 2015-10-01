package com.callfire.api.client.api.numbers;

import com.callfire.api.client.CallfireApiException;
import com.callfire.api.client.CallfireClientException;
import com.callfire.api.client.RestApiClient;
import com.callfire.api.client.api.numbers.model.NumberConfig;
import com.callfire.api.client.api.numbers.model.NumberLease;
import com.callfire.api.client.api.common.model.Page;
import com.callfire.api.client.api.numbers.model.request.FindNumberLeaseConfigsRequest;
import com.callfire.api.client.api.numbers.model.request.FindNumberLeasesRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.callfire.api.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api.client.ClientConstants.Type.VOID_TYPE;
import static com.callfire.api.client.ClientUtils.addQueryParamIfSet;

/**
 * Represents rest endpoint /numbers/leases
 *
 * @since 1.0
 */
public class NumberLeasesApi {
    private static final String NUMBER_LEASES_PATH = "/numbers/leases";
    private static final String NUMBER_LEASES_ITEM_PATH = "/numbers/leases/{}";
    private static final String NUMBER_CONFIGS_PATH = "/numbers/leases/configs";
    private static final String NUMBER_CONFIGS_ITEM_PATH = "/numbers/leases/configs/{}";

    private static final TypeReference<Page<NumberLease>> PAGE_OF_NUMBER_LEASES_TYPE = new TypeReference<Page<NumberLease>>() {
    };
    private static final TypeReference<Page<NumberConfig>> PAGE_OF_NUMBER_CONFIGS_TYPE = new TypeReference<Page<NumberConfig>>() {
    };
    private static final TypeReference<NumberLease> NUMBER_LEASE_TYPE = new TypeReference<NumberLease>() {
    };
    private static final TypeReference<NumberConfig> NUMBER_CONFIG_TYPE = new TypeReference<NumberConfig>() {
    };

    private RestApiClient client;

    public NumberLeasesApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Find number leases by prefix, zipcode, etc...
     *
     * @param request request payload
     * @return page of number leases
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public Page<NumberLease> find(FindNumberLeasesRequest request) {
        return client.get(NUMBER_LEASES_PATH, PAGE_OF_NUMBER_LEASES_TYPE, request);
    }

    /**
     * Get number lease by number
     *
     * @param number leased phone number
     * @return object which represents number lease
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public NumberLease get(String number) {
        return get(number, null);
    }

    /**
     * Get number lease by number
     *
     * @param number leased phone number
     * @param fields Limit fields returned. Example fields=id,name
     * @return object which represents number lease
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public NumberLease get(String number, String fields) {
        Validate.notBlank(number, "number cannot be blank");
        List<NameValuePair> queryParams = new ArrayList<>();
        addQueryParamIfSet("fields", fields, queryParams);
        return client.get(NUMBER_LEASES_ITEM_PATH.replaceFirst(PLACEHOLDER, number), NUMBER_LEASE_TYPE, queryParams);
    }

    /**
     * Update number lease
     *
     * @param lease update payload
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public void update(NumberLease lease) {
        Validate.notBlank(lease.getNumber(), "lease.number cannot be blank");
        client.put(NUMBER_LEASES_ITEM_PATH.replaceFirst(PLACEHOLDER, lease.getNumber()), VOID_TYPE, lease);
    }

    /**
     * Find number lease configs
     *
     * @param request request payload
     * @return page of number leases configs
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public Page<NumberConfig> findConfigs(FindNumberLeaseConfigsRequest request) {
        return client.get(NUMBER_CONFIGS_PATH, PAGE_OF_NUMBER_CONFIGS_TYPE, request);
    }

    /**
     * Get number lease config
     *
     * @param number leased phone number
     * @return object which represents number lease config
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public NumberConfig getConfig(String number) {
        return getConfig(number, null);
    }

    /**
     * Get number lease config
     *
     * @param number leased phone number
     * @param fields Limit fields returned. Example fields=id,name
     * @return object which represents number lease config
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public NumberConfig getConfig(String number, String fields) {
        Validate.notBlank(number, "number cannot be blank");
        List<NameValuePair> queryParams = new ArrayList<>();
        addQueryParamIfSet("fields", fields, queryParams);
        return client.get(NUMBER_CONFIGS_ITEM_PATH.replaceFirst(PLACEHOLDER, number), NUMBER_CONFIG_TYPE, queryParams);
    }

    /**
     * Update number lease config
     *
     * @param config update payload
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public void updateConfig(NumberConfig config) {
        Validate.notBlank(config.getNumber(), "config.number cannot be blank");
        client.put(NUMBER_CONFIGS_ITEM_PATH.replaceFirst(PLACEHOLDER, config.getNumber()), VOID_TYPE, config);
    }

}