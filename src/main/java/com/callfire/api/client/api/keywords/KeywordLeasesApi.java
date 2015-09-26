package com.callfire.api.client.api.keywords;

import com.callfire.api.client.CallfireApiException;
import com.callfire.api.client.CallfireClientException;
import com.callfire.api.client.RestApiClient;
import com.callfire.api.client.api.common.model.Page;
import com.callfire.api.client.api.common.model.request.CommonFindRequest;
import com.callfire.api.client.api.keywords.model.KeywordLease;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.Validate;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import static com.callfire.api.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api.client.ClientConstants.Type.VOID_TYPE;
import static com.callfire.api.client.ClientUtils.addQueryParamIfSet;

/**
 * Represents rest endpoint /keywords/leases
 */
public class KeywordLeasesApi {
    private static final String KEYWORD_LEASES = "/keywords/leases";
    private static final String KEYWORD_LEASES_ITEM = "/keywords/leases/{}";

    private static final TypeReference<Page<KeywordLease>> PAGE_OF_KEYWORD_LEASES_TYPE = new TypeReference<Page<KeywordLease>>() {
    };
    private static final TypeReference<KeywordLease> KEYWORD_LEASE_TYPE = new TypeReference<KeywordLease>() {
    };

    private RestApiClient client;

    public KeywordLeasesApi(RestApiClient client) {
        this.client = client;
    }

    /**
     * Find keyword leases
     *
     * @param request request payload
     * @return page of keyword leases
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public Page<KeywordLease> find(CommonFindRequest request) {
        return client.get(KEYWORD_LEASES, PAGE_OF_KEYWORD_LEASES_TYPE, request);
    }

    /**
     * Get keyword lease by keyword
     *
     * @param keyword leased keyword
     * @return object which represents keyword lease
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public KeywordLease get(String keyword) {
        return get(keyword, null);
    }

    /**
     * Get keyword lease by keyword
     *
     * @param keyword leased keyword
     * @param fields  Limit fields returned. Example fields=id,name
     * @return object which represents keyword lease
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public KeywordLease get(String keyword, String fields) {
        Validate.notBlank(keyword, "keyword cannot be blank");
        List<NameValuePair> queryParams = new ArrayList<>(1);
        addQueryParamIfSet("fields", fields, queryParams);
        return client.get(KEYWORD_LEASES_ITEM.replaceFirst(PLACEHOLDER, keyword), KEYWORD_LEASE_TYPE, queryParams);
    }

    /**
     * Update keyword lease
     *
     * @param lease keyword lease payload
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public void update(KeywordLease lease) {
        Validate.notBlank(lease.getKeyword(), "lease.keyword cannot be blank");
        client.put(KEYWORD_LEASES_ITEM.replaceFirst(PLACEHOLDER, lease.getKeyword()), VOID_TYPE, lease);
    }
}
