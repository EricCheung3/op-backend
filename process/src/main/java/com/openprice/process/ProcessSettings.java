package com.openprice.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.process")
public class ProcessSettings {

    @Getter @Setter
    private List<String> servers = new ArrayList<String>();

    @Getter @Setter
    private String serverPrefix;

    @Getter @Setter
    private int serverPort = 7901;  // default to 7901 for OCR Server port

    @Getter @Setter
    private int numberOfServers = 0;
}
