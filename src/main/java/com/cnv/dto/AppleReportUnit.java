package com.cnv.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class AppleReportUnit {

    private String provider;

    private String providerCountry;

    private String sku;

    private String developer;

    private String title;

    private String version;

    private String productTypeIdentifier;

    private int units;

    private int developerProceeds;

    private Date beginDate;

    private Date endDate;

    private String customerCurrency;

    private String countryCode;

    private String currencyOfProcess;

    private String appleIdentifier;

    private float customerPrice;

    private String promoCode;

    private String parentIdentifier;

    private String subscription;

    private String period;

    public AppleReportUnit() {

    }

    @Builder
    public AppleReportUnit(String provider, String providerCountry, String sku, String developer, String title, String version,
                           String productTypeIdentifier, int units, int developerProceeds, Date beginDate, Date endDate,
                           String customerCurrency, String countryCode, String currencyOfProcess, String appleIdentifier,
                           float customerPrice, String promoCode, String parentIdentifier, String subscription, String period) {
        this.provider = provider;
        this.providerCountry = providerCountry;
        this.sku = sku;
        this.developer = developer;
        this.title = title;
        this.version = version;
        this.productTypeIdentifier = productTypeIdentifier;
        this.units = units;
        this.developerProceeds = developerProceeds;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.customerCurrency = customerCurrency;
        this.countryCode = countryCode;
        this.currencyOfProcess = currencyOfProcess;
        this.appleIdentifier = appleIdentifier;
        this.customerPrice = customerPrice;
        this.promoCode = promoCode;
        this.parentIdentifier = parentIdentifier;
        this.subscription = subscription;
        this.period = period;
    }

}