package com.cnv.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;

public class DateTimeUtilTest {
    @Test
    public void testGetNextSundayOfDate() {
        DateTime date = new DateTime(2016, 11, 8, 0, 0, 0, DateTimeZone.UTC);
        DateTime actual = DateTimeUtil.getNextSundayOfDate(date);
        Assert.assertEquals(new DateTime(2016, 11, 13, 0, 0, 0, DateTimeZone.UTC), actual);
    }
}
