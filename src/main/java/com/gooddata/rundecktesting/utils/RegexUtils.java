/*
 * Copyright (C) 2007-2017, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.rundecktesting.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private RegexUtils() {}

    public static String getGroupOrThrow(final Pattern pattern, final String input, final int group) {
        final Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(group);
        } else {
            throw new IllegalStateException("'" + input + "' does not match pattern " + pattern);
        }
    }

    public static String getGroupOrNull(final Pattern pattern, final String input, final int group) {
        final Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(group);
        } else {
            return null;
        }
    }
}
