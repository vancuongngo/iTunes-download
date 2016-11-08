package com.cnv.controller;

import com.cnv.service.ITunesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    ITunesService iTunesService;


    @RequestMapping(value = {"", "/", "/index"})
    public String index() {
        return "Hi friend!";
    }
}
