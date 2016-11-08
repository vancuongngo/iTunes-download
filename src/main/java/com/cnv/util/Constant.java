package com.cnv.util;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constant {
    public enum ReportType {
        Sales, PreOrder, Cloud, Event, Customer, Content, Station, Control, amEvent, amContent, amControl, amStreams
    }

    public enum ReportSubType {
        Summary, Detailed
    }

    public enum DateType {
        Daily, Weekly, Monthly, Yearly
    }

    public static final DateTimeFormatter yearlyITunesReportFormatter = DateTimeFormat.forPattern("yyyy");
    public static final DateTimeFormatter monthlyITunesReportFormatter = DateTimeFormat.forPattern("yyyyMM");
    public static final DateTimeFormatter dailyITunesReportFormatter = DateTimeFormat.forPattern("yyyyMMdd");

    public static final String SUCCESSFULLY_DOWNLOADED = "Successfully downloaded";
    public static final List<String> UPDATE_PRODUCT_TYPE_IDENTIFIER = Collections.unmodifiableList(Arrays.asList("3","7", "7F", "7T", "F7"));
    public static final String IOS_URL_PATTERN = "^https://itunes.apple.com/(.+?)/app/(.*?)/id(.*?)\\?(.+?)$";
}
