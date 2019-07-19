package com.callfire.api.client.api.contacts.model.request;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import com.callfire.api.client.api.common.model.request.FindRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Request object for GET /contacts which incapsulates
 * different query pairs
 */
@Getter
@NoArgsConstructor(access = PRIVATE)
public class FindContactsRequest extends FindRequest {

    /**
     * Particular contact list id to search by
     *
     * @return particular contact list id to search by
     */
    private Long contactListId;

    /**
     * Name of contact property to search by
     *
     * @return name of contact property to search by
     */
    private String propertyName;

    /**
     * Value of contact property to search by
     *
     * @return value of contact property to search by
     */
    private String propertyValue;

    /**
     * Multiple contact numbers can be specified. If the number parameter is included,
     * the other query parameters are ignored.
     *
     * @return contact numbers
     */
    private List<String> number;

    /**
     * Multiple contact ids can be specified. If the id parameter is included,
     * the other query parameters are ignored.
     *
     * @return contact ids
     */
    private List<Long> id;

    /**
     * Create request builder
     *
     * @return request build
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Builder class for find method
     */
    public static class Builder extends FindRequestBuilder<Builder, FindContactsRequest> {
        private Builder() {
            super(new FindContactsRequest());
        }

        /**
         * A particular contact list to search by
         *
         * @param contactListId particular contact list to search by
         * @return builder self reference
         */
        public Builder contactListId(Long contactListId) {
            request.contactListId = contactListId;
            return this;
        }

        /**
         * Set name of contact property to search by
         *
         * @param propertyName name of contact property to search by
         * @return builder self reference
         */
        public Builder propertyName(String propertyName) {
            request.propertyName = propertyName;
            return this;
        }

        /**
         * Set value of contact property to search by
         *
         * @param propertyValue value of contact property to search by
         * @return builder self reference
         */
        public Builder propertyValue(String propertyValue) {
            request.propertyValue = propertyValue;
            return this;
        }

        /**
         * Multiple contact numbers can be specified. If the number parameter is included,
         * the other query parameters are ignored.
         *
         * @param number list of numbers to query
         * @return builder self reference
         */
        public Builder number(List<String> number) {
            request.number = number;
            return this;
        }

        /**
         * Multiple contact ids can be specified. If the id parameter is included,
         * the other query parameters are ignored.
         *
         * @param id contact ids to search by
         * @return builder self reference
         */
        public Builder id(List<Long> id) {
            request.id = id;
            return this;
        }
    }
}
