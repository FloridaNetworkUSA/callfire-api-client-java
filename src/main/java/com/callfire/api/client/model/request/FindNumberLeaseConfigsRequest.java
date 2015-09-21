package com.callfire.api.client.model.request;

/**
 * Request object for searching number lease configs which incapsulates
 * different query pairs
 */
public class FindNumberLeaseConfigsRequest extends FindByRegionDataRequest {
    private String labelName;

    protected FindNumberLeaseConfigsRequest() {
    }

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    public String getLabelName() {
        return labelName;
    }

    /**
     * Builder class
     */
    public static class Builder extends RegionDataBuilder<Builder, FindNumberLeaseConfigsRequest> {

        protected Builder() {
            super(new FindNumberLeaseConfigsRequest());
        }

        public Builder setLabelName(String labelName) {
            request.labelName = labelName;
            return this;
        }
    }
}
