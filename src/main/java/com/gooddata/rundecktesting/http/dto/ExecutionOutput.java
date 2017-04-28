/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutionOutput {

    private final boolean completed;
    private final List<ExecutionOutputEntry> entries;

    // TODO add all fields


    @JsonCreator
    public ExecutionOutput(
            @JsonProperty("completed") final boolean completed,
            @JsonProperty("entries") final List<ExecutionOutputEntry> entries
    ) {

        this.completed = completed;
        this.entries = entries;
    }

    public boolean isCompleted() {
        return completed;
    }

    public List<ExecutionOutputEntry> getEntries() {
        return entries;
    }
}
