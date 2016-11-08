package com.cnv.service;

import com.cnv.dto.ReporterArgument;
import com.cnv.util.Constant;
import org.fest.reflect.core.Reflection;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ITunesService.class})
@SpringBootTest
public class ITunesServiceTest extends ServiceBaseTest {

    @InjectMocks
    ITunesService iTunesService;

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

    @Test
    public void testHandleReportFileDownloadFromITunes() throws Exception {
        String fileName = "S_M_123456789_201601.txt.gz";
        String appleStoreId = "284882215";
        DateTime queryDate = new DateTime(2016, 1, 15, 0, 0, 0, DateTimeZone.UTC);
        ReporterArgument reporterArgument = Reflection.method("buildReporterArgument")
                .withReturnType(ReporterArgument.class)
                .withParameterTypes(DateTime.class, Constant.DateType.class)
                .in(iTunesService)
                .invoke(queryDate, Constant.DateType.Monthly);

        ClassLoader classLoader = getClass().getClassLoader();
        File fileMock = new File(classLoader.getResource("itunes/S_M_123456789_201601.txt.gz").getFile());
        PowerMockito.whenNew(File.class).withParameterTypes(String.class).withArguments(Mockito.anyString()).thenReturn(fileMock);
        PowerMockito.stub(PowerMockito.method(File.class, "delete")).toReturn(true);

        int actual = Reflection.method("handleReportFileDownloadFromITunes")
                .withReturnType(int.class)
                .withParameterTypes(String.class, ReporterArgument.class, String.class)
                .in(iTunesService)
                .invoke(fileName, reporterArgument, appleStoreId);
        Assert.assertEquals(3, actual);
    }
}
