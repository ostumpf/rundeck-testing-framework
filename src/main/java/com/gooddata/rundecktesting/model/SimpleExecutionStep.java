/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.model;

import com.gooddata.rundecktesting.http.dto.ExecutionOutputEntry;

public class SimpleExecutionStep extends ExecutionStep {

    private SimpleExecutionStep(final String jobName, final String node) {
        super(jobName, node);
    }

    public static SimpleExecutionStep from(final ExecutionOutputEntry entry) {
        final String jobName = getJobNameFromLog(entry.getLog());
        return new SimpleExecutionStep(jobName, entry.getNodeName());
    }
}
