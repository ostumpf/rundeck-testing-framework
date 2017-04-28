/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooddata.rundecktesting.http.dto.ExecutionOutput;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class RestClient {

    private static final String ACCEPT_HEADER = "Accept";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String EXECUTION_OUTPUT_FORMAT = "/api/17/execution/%s/output";
    private static final String APPLICATION_JSON = "application/json";

    private final String hostname;
    private final String authorization;

    public RestClient(final String hostname, final String authorization) {
        this.hostname = hostname;
        this.authorization = authorization;
    }

    public ExecutionOutput getExecutionOutput(final String executionId) throws IOException {
        final URL executionUrl = new URL(getExecutionUrl(executionId));

        final URLConnection urlConnection = executionUrl.openConnection();
        urlConnection.addRequestProperty(AUTHORIZATION_HEADER, authorization);
        urlConnection.addRequestProperty(ACCEPT_HEADER, APPLICATION_JSON);
        urlConnection.connect();

        return new ObjectMapper().readValue(urlConnection.getInputStream(), ExecutionOutput.class);
    }

    private String getExecutionUrl(final String executionId) {
        return "https://" + hostname + String.format(EXECUTION_OUTPUT_FORMAT, executionId);
    }
}
