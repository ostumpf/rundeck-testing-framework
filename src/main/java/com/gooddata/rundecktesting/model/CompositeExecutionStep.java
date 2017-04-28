/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.model;

import com.gooddata.rundecktesting.http.dto.ExecutionOutputEntry;

import java.util.List;

public class CompositeExecutionStep extends ExecutionStep {

    private final List<ExecutionStep> children;

    private CompositeExecutionStep(final String jobName, final String node, final List<ExecutionStep> children) {
        super(jobName, node);

        this.children = children;
    }

    public List<ExecutionStep> getChildren() {
        return children;
    }

    public static CompositeExecutionStep from(final ExecutionOutputEntry entry,
                                              final List<ExecutionStep> childExecutionSteps) {
        final String jobName = getJobNameFromLog(entry.getLog());
        return new CompositeExecutionStep(jobName, entry.getNodeName(), childExecutionSteps);
    }
}
