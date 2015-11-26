package com.openprice.internal.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("application.service.internal")
@Data
public class InternalServiceSettings {

    private String serverHost;

    private int serverPort;

}
