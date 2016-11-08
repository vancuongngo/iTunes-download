package com.cnv.dto;

import com.cnv.util.Constant;
import lombok.Builder;
import lombok.Data;

@Data
public class ReporterArgument {
    private int vendorId;
    private Constant.ReportType reportType;
    private Constant.ReportSubType reportSubType;
    private Constant.DateType dateType;
    private String date;

    @Builder
    public ReporterArgument(int vendorId, Constant.ReportType reportType, Constant.ReportSubType reportSubType, Constant.DateType dateType, String date) {
        this.vendorId = vendorId;
        this.reportType = reportType;
        this.reportSubType = reportSubType;
        this.dateType = dateType;
        this.date = date;
    }

    public String[] buildArgument() {
        StringBuilder result = new StringBuilder();
        result.append("p=Reporter.properties/t");
        result.append("Sales.getReport/t");
        result.append(this.vendorId + ",/t");
        result.append(this.reportType + ",/t");
        result.append(this.reportSubType + ",/t");
        result.append(this.dateType + ",/t");
        result.append(this.date);
        return result.toString().split("/t");
    }
}
