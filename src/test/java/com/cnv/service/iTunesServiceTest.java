package com.cnv.service;

import com.cnv.util.Constant;
import org.fest.reflect.core.Reflection;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ITunesServiceTest {

    @Autowired
    ITunesService iTunesService;

    @Test
    public void test() {
        DateTime queryDate = new DateTime(2016, 1, 15, 0, 0, 0, DateTimeZone.UTC);
        System.out.println(iTunesService.getReportFile(queryDate, Constant.DateType.Monthly));
    }

    @Test
    public void testGetAppleStoreIdSuccess() {
        String iTunesAppUrl = "https://itunes.apple.com/vn/app/facebook/id284882215?mt=8";
        String actual = Reflection.method("getAppleStoreId")
                .withReturnType(String.class)
                .withParameterTypes(String.class)
                .in(iTunesService)
                .invoke(iTunesAppUrl);
        Assert.assertEquals("284882215", actual);
    }

    @Test
    public void testGetAppleStoreIdFail() {
        String iTunesAppUrl = "https://itunes.apple.com/vn/app-wrong-pattern/facebook/id284882215?mt=8";
        String actual = Reflection.method("getAppleStoreId")
                .withReturnType(String.class)
                .withParameterTypes(String.class)
                .in(iTunesService)
                .invoke(iTunesAppUrl);
        Assert.assertNull(actual);
    }
}
