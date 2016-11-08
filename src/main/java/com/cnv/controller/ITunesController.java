package com.cnv.controller;

import com.cnv.service.ITunesService;
import com.cnv.util.Constant;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ITunesController {

    @Autowired
    ITunesService iTunesService;

    @RequestMapping(value = "/iTunes-download", method = RequestMethod.GET)
    public int getITunesDownload() {
        DateTime yesterday = DateTime.now().minusDays(1);
        return iTunesService.getReportFile(yesterday, Constant.DateType.Monthly);
    }
}
