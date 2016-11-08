package com.cnv.service;

import com.apple.gbi.autoingest.client.Reporter;
import com.cnv.dto.AppleReportUnit;
import com.cnv.dto.ReporterArgument;
import com.cnv.util.Constant;
import com.cnv.util.DateTimeUtil;
import com.cnv.util.LoggedPrintStream;
import com.cnv.util.XMLParseUtil;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

@Service
@PropertySource("classpath:local.properties")
public class ITunesService {

    public static final Logger logger = LoggerFactory.getLogger(ITunesService.class);

    @Value("${VENDOR_NUMBER}")
    private int vendorId;

    @Value("${ITUNES_APP_URL}")
    private String iTunesAppUrl;

    public int getReportFile(DateTime queryDate, Constant.DateType dateType) {
        String appleStoreId = getAppleStoreId(iTunesAppUrl);

        ReporterArgument reporterArgument = buildReporterArgument(queryDate, dateType);

        LoggedPrintStream lpsOut = LoggedPrintStream.create(System.out);
        System.setOut(lpsOut);
        Reporter.main(reporterArgument.buildArgument());
        System.setOut(lpsOut.getUnderlying());
        String responseFromITunes = lpsOut.getBuf().toString();
        Document doc = XMLParseUtil.convertStringToDocument(responseFromITunes);
        NodeList outputNodes = doc.getElementsByTagName("Output");

        int result = 0;
        if (outputNodes.getLength() > 0) {
            Element element = (Element) outputNodes.item(0);
            String message = element.getElementsByTagName("Message").item(0).getTextContent();
            String fileName;
            if (message.contains(Constant.SUCCESSFULLY_DOWNLOADED)) {
                fileName = message.split(" ")[2];
                result = handleReportFileDownloadFromITunes(fileName, reporterArgument, appleStoreId);
            } else {
                logger.debug(message);
            }
        }
        return result;
    }

    private ReporterArgument buildReporterArgument(DateTime queryDate, Constant.DateType dateType) {
        ReporterArgument reporterArgument = ReporterArgument.builder()
                .vendorId(vendorId)
                .reportType(Constant.ReportType.Sales)
                .reportSubType(Constant.ReportSubType.Summary)
                .dateType(dateType)
                .build();
        switch (dateType) {
            case Weekly:
                queryDate = DateTimeUtil.getNextSundayOfDate(queryDate);
            case Daily:
                reporterArgument.setDate(Constant.dailyITunesReportFormatter.print(queryDate));
                break;
            case Monthly:
                reporterArgument.setDate(Constant.monthlyITunesReportFormatter.print(queryDate));
                break;
            case Yearly:
                reporterArgument.setDate(Constant.yearlyITunesReportFormatter.print(queryDate));
                break;
            default:
        }
        return reporterArgument;
    }

    private int handleReportFileDownloadFromITunes(String fileName, ReporterArgument reporterArgument, String appleStoreId) {
        String[] fileNameParts = fileName.split("\\s+|_|\\.");
        String fileNameExpected = fileNameParts[fileNameParts.length - 3];
        int numberOfDownloads = 0;
        File file = new File(fileName);
        if (fileNameExpected.equals(reporterArgument.getDate())) {
            try {
                String appleReportStr = decompressAppleReport(new FileInputStream(file));
                String[] appleReportArr = appleReportStr.split("\n");

                List<AppleReportUnit> appleReportUnits = new ArrayList<>();
                for (int i = 1; i < appleReportArr.length; i++) {
                    appleReportUnits.add(getAppleReportElement(appleReportArr[i].trim().split("\t")));
                }

                for (AppleReportUnit appleReportUnit : appleReportUnits) {
                    if (!Constant.UPDATE_PRODUCT_TYPE_IDENTIFIER.contains(appleReportUnit.getProductTypeIdentifier())
                            && appleStoreId.equals(appleReportUnit.getAppleIdentifier())) {
                        numberOfDownloads += appleReportUnit.getUnits();
                    }
                }
            } catch (IOException e) {
                logger.error("Error when processing Apple's Reporter file {}", e);
            }
        }
        file.delete();
        return numberOfDownloads;
    }

    private String decompressAppleReport(InputStream input) throws IOException {
        try (GZIPInputStream in = new GZIPInputStream(input);
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                output.write(buffer, 0, len);
            }
            in.close();
            output.close();
            return output.toString(Charset.forName("UTF-8").name());
        }
    }

    private AppleReportUnit getAppleReportElement(String[] appleReportElementArr) {
        return AppleReportUnit.builder().productTypeIdentifier(appleReportElementArr[6])
                .units(Integer.parseInt(appleReportElementArr[7]))
                .appleIdentifier(appleReportElementArr[14])
                .build();
    }

    private String getAppleStoreId(String iTunesAppUrl) {
        Pattern iosUrlPattern = Pattern.compile(Constant.IOS_URL_PATTERN);
        Matcher matcher = iosUrlPattern.matcher(iTunesAppUrl);
        String appStoreId = null;
        if (matcher.matches()) {
            appStoreId = matcher.group(3);
        }
        return appStoreId;
    }
}
