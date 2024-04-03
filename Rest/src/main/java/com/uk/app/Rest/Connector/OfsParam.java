package com.uk.app.Rest.Connector;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author odavies
 */
@Getter
public class OfsParam {

    private String operation;

    private final String version;

    private final List<String> options;

    private final String messageReference;

    private final String transactionId;

    private final List<String> credentials;

    private final List<DataItem> dataItems;

    public OfsParam(OfsParamBuilder builder) {
        this.dataItems = builder.getDataItems();
        this.credentials = builder.getCredentials();
        this.messageReference = builder.getMessageReference();
        this.operation = builder.getOperation();
        this.options = builder.getOptions();
        this.transactionId = builder.getTransactionId();
        this.version = builder.getVersion();
    }

    @Data
    @AllArgsConstructor
    public static class DataItem {

        private String itemHeader;

        private List<String> itemValues;
    }

    @Data
    public static class OfsParamBuilder {

        private String operation;

        private String version;

        private List<String> options;

        private String messageReference;

        private String transactionId;

        private List<String> credentials;

        private List<DataItem> dataItems = new ArrayList<>();

        public OfsParamBuilder(String operation, String version, List<String> credentials) {
            this.operation = operation;
            this.version = version;
            this.credentials = credentials;
        }

        /**
         * @param operation the operation to set
         * @return
         */
        public OfsParamBuilder setOperation(String operation) {
            this.operation = operation;
            return this;
        }

        /**
         * @param version the version to set
         * @return
         */
        public OfsParamBuilder setVersion(String version) {
            this.version = version;
            return this;
        }

        /**
         * @param options the options to set
         * @return
         */
        public OfsParamBuilder setOptions(List<String> options) {
            this.options = options;
            return this;
        }

        /**
         * @param messageReference the messageReference to set
         * @return
         */
        public OfsParamBuilder setMessageReference(String messageReference) {
            this.messageReference = messageReference;
            return this;
        }

        /**
         * @param transactionId the transactionId to set
         * @return
         */
        public OfsParamBuilder setTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        /**
         * @param credentials the credentials to set
         * @return
         */
        public OfsParamBuilder setCredentials(List<String> credentials) {
            this.credentials = credentials;
            return this;
        }

        /**
         * @param dataItem the dataItem to add
         * @return
         */
        public OfsParamBuilder addDataItem(DataItem dataItem) {
            dataItems.add(dataItem);
            return this;
        }

        public OfsParam build() {
            OfsParam ofsp = new OfsParam(this);

            return ofsp;
        }

    }

}