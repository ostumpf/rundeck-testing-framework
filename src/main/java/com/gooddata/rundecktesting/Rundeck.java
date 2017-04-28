/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting;

import com.gooddata.rundecktesting.http.RestClient;
import com.gooddata.rundecktesting.http.dto.ExecutionOutput;
import com.gooddata.rundecktesting.http.dto.ExecutionOutputEntry;
import com.gooddata.rundecktesting.model.CompositeExecutionStep;
import com.gooddata.rundecktesting.model.ExecutionStep;
import com.gooddata.rundecktesting.model.SimpleExecutionStep;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.gooddata.rundecktesting.utils.RegexUtils.getGroupOrNull;
import static com.gooddata.rundecktesting.utils.RegexUtils.getGroupOrThrow;

public class Rundeck {

    public static void main(String[] args) throws IOException {
        // TODO: set your auth
        final String auth = "Basic base64base64base64base64";
        System.out.println(new Rundeck("rundeck-dev.na.intgdc.com", auth).getExecutionSteps("2688488").size());
    }

    private static final Pattern JOB_TRIGGER_LOG_PATTERN = Pattern.compile("INFO execution_link:\\s+(https://[^\\s]+)$");
    private static final Pattern JOB_TRIGGER_LINK_PATTERN = Pattern.compile("/execution/show/([^\\s]+)$");

    private final String hostname;
    private final String authorization;

    public Rundeck(final String hostname, final String authorization) {
        this.hostname = hostname;
        this.authorization = authorization;
    }

    public List<ExecutionStep> getExecutionSteps(final String executionId) {
        final RestClient restClient = new RestClient(hostname, authorization);

        return getExecutionSteps(restClient, executionId);
    }

    private List<ExecutionStep> getExecutionSteps(final RestClient restClient, final String executionId) {
        final ExecutionOutput executionOutput;
        try {
            executionOutput = restClient.getExecutionOutput(executionId);
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }

        return executionOutput.getEntries().stream()
                .map(e -> getExecutionStep(restClient, e))
                .collect(Collectors.toList());
    }

    private ExecutionStep getExecutionStep(final RestClient restClient, final ExecutionOutputEntry entry) {
        final String jobTriggerLink = getJobTriggerLink(entry);

        if (jobTriggerLink == null) {
            return SimpleExecutionStep.from(entry);
        } else {
            final String childExecutionId = getChildExecutionId(jobTriggerLink);
            final List<ExecutionStep> childExecutionSteps = getExecutionSteps(restClient, childExecutionId);
            return CompositeExecutionStep.from(entry, childExecutionSteps);
        }
    }

    private String getChildExecutionId(final String jobTriggerLink) {
        return getGroupOrThrow(JOB_TRIGGER_LINK_PATTERN, jobTriggerLink, 1);
    }

    private String getJobTriggerLink(final ExecutionOutputEntry entry) {
        return getGroupOrNull(JOB_TRIGGER_LOG_PATTERN, entry.getLog(), 1);
    }
}
