package com.cnv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@PropertySource(value = "local.properties")
public class ITunesDownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ITunesDownloadApplication.class, args);
	}
}
