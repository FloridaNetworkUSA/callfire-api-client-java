package com.callfire.api.client.endpoint;

import com.callfire.api.client.CallfireApiException;
import com.callfire.api.client.CallfireClientException;
import com.callfire.api.client.RestApiClient;
import com.callfire.api.client.model.Number;
import com.callfire.api.client.model.Page;
import com.callfire.api.client.model.Region;
import com.callfire.api.client.model.request.FindNumberRegionsRequest;
import com.callfire.api.client.model.request.FindNumbersLocalRequest;
import com.callfire.api.client.model.request.FindNumbersTollfreeRequest;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * Represents rest endpoint /numbers
 */
public class NumbersEndpoint {
    private static final String NUMBERS_LOCAL = "/numbers/local";
    private static final String NUMBERS_REGIONS = "/numbers/regions";
    private static final String NUMBERS_TOLLFREE = "/numbers/tollfree";

    private static final TypeReference<List<Number>> NUMBERS_LIST_TYPE = new TypeReference<List<Number>>() {
    };
    private static final TypeReference<Page<Region>> PAGE_OF_REGION_TYPE = new TypeReference<Page<Region>>() {
    };

    private RestApiClient client;
    private NumberLeasesEndpoint numberLeasesEndpoint;

    public NumbersEndpoint(RestApiClient client) {
        this.client = client;
    }

    /**
     * Find number in local catalog by prefix, zipcode, etc...
     *
     * @param request request payload
     * @return available numbers in catalog
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public List<Number> findNumbersLocal(FindNumbersLocalRequest request) {
        return client.get(NUMBERS_LOCAL, NUMBERS_LIST_TYPE, request);
    }

    /**
     * Get region data for numbers by prefix, zipcode, etc...
     *
     * @param request request payload
     * @return page of region objects
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public Page<Region> findNumberRegions(FindNumberRegionsRequest request) {
        return client.get(NUMBERS_REGIONS, PAGE_OF_REGION_TYPE, request);
    }

    /**
     * Find numbers in tollfree catalog
     *
     * @param request request payload
     * @return NumberOrder
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public List<Number> findNumbersTollfree(FindNumbersTollfreeRequest request) {
        return client.get(NUMBERS_TOLLFREE, NUMBERS_LIST_TYPE, request);
    }

    /**
     * Get /numbers/leases api endpoint
     *
     * @return endpoint object
     */
    public NumberLeasesEndpoint getNumberLeasesEndpoint() {
        if (numberLeasesEndpoint == null) {
            numberLeasesEndpoint = new NumberLeasesEndpoint(client);
        }
        return numberLeasesEndpoint;
    }
}
