/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.model;

import java.util.regex.Pattern;

import static com.gooddata.rundecktesting.utils.RegexUtils.getGroupOrThrow;

public abstract class ExecutionStep {

    private static final Pattern JOB_NAME_PATTERN = Pattern.compile("^rundeck_trigger\\.build\\.([^:]+):");
    private final String jobName;
    private final String node;

    protected ExecutionStep(final String jobName, final String node) {
        this.jobName = jobName;
        this.node = node;
    }

    public String getJobName() {
        return jobName;
    }

    public String getNode() {
        return node;
    }

    protected static String getJobNameFromLog(final String log) {
        return getGroupOrThrow(JOB_NAME_PATTERN, log, 1);
    }
}
