package com.callfire.api.client.endpoint.campaigns;

import com.callfire.api.client.CallfireApiException;
import com.callfire.api.client.CallfireClientException;
import com.callfire.api.client.RestApiClient;
import com.callfire.api.client.model.Page;
import com.callfire.api.client.model.ResourceId;
import com.callfire.api.client.model.campaigns.TextAutoReply;
import com.callfire.api.client.model.request.FindTextAutoRepliesRequest;
import com.callfire.api.client.model.request.FindTextAutoRepliesRequest.Builder;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.callfire.api.client.ClientConstants.Type.RESOURCE_ID_TYPE;
import static com.callfire.api.client.ClientConstants.PLACEHOLDER;
import static com.callfire.api.client.ClientUtils.addQueryParamIfSet;

/**
 * Represents rest endpoint /campaigns/text-auto-replys
 */
public class TextAutoRepliesEndpoint {
    private static final String TEXT_AUTO_REPLIES_PATH = "/campaigns/text-auto-replys";
    private static final String TEXT_AUTO_REPLIES_ITEM_PATH = "/campaigns/text-auto-replys/{}";
    private static final TypeReference<TextAutoReply> TEXT_AUTO_REPLY_TYPE = new TypeReference<TextAutoReply>() {
    };
    private static final TypeReference<Page<TextAutoReply>> PAGE_OF_TEXT_AUTO_REPLY_TYPE = new TypeReference<Page<TextAutoReply>>() {
    };

    private RestApiClient client;

    public TextAutoRepliesEndpoint(RestApiClient client) {
        this.client = client;
    }

    /**
     * Query for text auto replies using optional number
     *
     * @param request request object with filtering options
     * @return page with TextAutoReply objects
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     * @see FindTextAutoRepliesRequest
     * @see Builder
     */
    public Page<TextAutoReply> findTextAutoReplies(FindTextAutoRepliesRequest request)
        throws CallfireApiException, CallfireClientException {
        return client.get(TEXT_AUTO_REPLIES_PATH, PAGE_OF_TEXT_AUTO_REPLY_TYPE, request);
    }

    /**
     * Create and configure new text auto reply message for existing number.
     * Auto-replies are text message replies sent to a customer when a customer replies to
     * a text message from a campaign. A keyword will need to have been purchased before an Auto-Reply can be created.
     *
     * @param textAutoReply auto-reply object to create
     * @return ResourceId object with id of created auto-reply
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public ResourceId createTextAutoReply(TextAutoReply textAutoReply)
        throws CallfireApiException, CallfireClientException {
        return client.post(TEXT_AUTO_REPLIES_PATH, RESOURCE_ID_TYPE, textAutoReply);
    }

    /**
     * Get text auto reply
     *
     * @param id id of text auto reply object
     * @return text auto reply object
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public TextAutoReply getTextAutoReply(Long id) throws CallfireApiException, CallfireClientException {
        return getTextAutoReply(id, null);
    }

    /**
     * Get text auto reply
     *
     * @param id     id of text auto reply object
     * @param fields limit fields returned. Example fields=id,message
     * @return text auto reply object
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public TextAutoReply getTextAutoReply(Long id, String fields) throws CallfireApiException, CallfireClientException {
        List<NameValuePair> queryParams = new ArrayList<>();
        addQueryParamIfSet("fields", fields, queryParams);
        String path = TEXT_AUTO_REPLIES_ITEM_PATH.replaceFirst(PLACEHOLDER, id.toString());
        return client.get(path, TEXT_AUTO_REPLY_TYPE, queryParams);
    }

    /**
     * Delete text auto reply message attached to number
     *
     * @param id id of text auto reply
     * @throws CallfireApiException    in case API cannot be queried for some reason and server returned error
     * @throws CallfireClientException in case error has occurred in client
     */
    public void deleteTextAutoReply(Long id) throws CallfireApiException, CallfireClientException {
        client.delete(TEXT_AUTO_REPLIES_ITEM_PATH.replaceFirst(PLACEHOLDER, Objects.toString(id)));
    }
}
