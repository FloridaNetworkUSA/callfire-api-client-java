package com.callfire.api.client.integration.endpoint;

import com.callfire.api.client.CallfireClient;
import com.callfire.api.client.model.Page;
import com.callfire.api.client.model.Webhook;
import com.callfire.api.client.model.request.FindWebhooksRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * integration tests for /webhooks api endpoint
 */
public class WebhooksEndpointTest extends AbstractIntegrationTest {
    @Rule
    public ExpectedException ex = ExpectedException.none();

    @Test
    public void testFindWebhooks() throws Exception {
        CallfireClient client = getCallfireClient();

        FindWebhooksRequest request = FindWebhooksRequest.create()
            .build();
        Page<Webhook> webhooks = client.getWebhooksEndpoint().findWebhooks(request);
        System.out.println(webhooks);

        expect404NotFoundCallfireApiException(ex);
        Webhook webhook = client.getWebhooksEndpoint().getWebhook(1L);
    }
}
