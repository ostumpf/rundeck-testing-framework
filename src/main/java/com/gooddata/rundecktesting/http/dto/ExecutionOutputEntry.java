/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.http.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExecutionOutputEntry {

    private final String log;
    private final String nodeName;

    // TODO add all fields


    @JsonCreator
    public ExecutionOutputEntry(
            @JsonProperty("log") final String log,
            @JsonProperty("node") final String nodeName
    ) {
        this.log = log;
        this.nodeName = nodeName;
    }

    public String getLog() {
        return log;
    }

    public String getNodeName() {
        return nodeName;
    }
}
