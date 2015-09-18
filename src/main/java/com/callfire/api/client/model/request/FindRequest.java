package com.callfire.api.client.model.request;

import com.callfire.api.client.model.BaseModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Contains common fields for finder endpoints
 */
public abstract class FindRequest extends BaseModel {
    protected Long limit;
    protected Long offset;
    protected String fields;

    /**
     * Get max number of records per page to return. If items.size() < limit assume no more items.
     *
     * @return limit number
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * Get offset to start of page
     *
     * @return offset
     */
    public Long getOffset() {
        return offset;
    }

    /**
     * Get limit fields returned. Example fields=id,items(name,agents(id))
     *
     * @return field to return
     */
    public String getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("limit", limit)
            .append("offset", offset)
            .append("fields", fields)
            .toString();
    }

    /**
     * Abstract builder for find requests
     *
     * @param <B> type of builder
     */
    @SuppressWarnings("unchecked")
    public static abstract class AbstractBuilder<B extends AbstractBuilder, R extends FindRequest> {
        protected final R request;

        protected AbstractBuilder(R request) {
            this.request = request;
        }

        /**
         * Set max number of items returned.
         *
         * @param limit max number of items
         * @return builder object
         */
        public B setLimit(Long limit) {
            request.limit = limit;
            return (B) this;
        }

        /**
         * Offset from start of paging source
         *
         * @param offset offset value
         * @return builder object
         */
        public B setOffset(Long offset) {
            request.offset = offset;
            return (B) this;
        }

        /**
         * Set limit fields returned. Example fields=id,items(name,agents(id))
         *
         * @param fields fields to return
         * @return builder object
         */
        public B setFields(String fields) {
            request.fields = fields;
            return (B) this;
        }

        /**
         * Build request
         *
         * @return find request pojo
         */
        public R build() {
            return request;
        }
    }
}
