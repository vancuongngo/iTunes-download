package com.cnv.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class DateTimeUtil {
    public static DateTime getNextSundayOfDate(DateTime date) {
        DateTime result = date.withDayOfWeek(DateTimeConstants.SUNDAY);
        return result;
    }
}
