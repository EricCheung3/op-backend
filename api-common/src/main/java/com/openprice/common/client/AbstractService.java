package com.openprice.common.client;

import org.springframework.web.client.RestTemplate;

public abstract class AbstractService {

    protected final RestTemplate restTemplate;
    protected final ServiceSettings settings;

    public AbstractService(final ServiceSettings settings) {
        this.restTemplate = new RestTemplate();
        this.settings = settings;
    }

    protected String getServiceUrlBase() {
        return "http://" + settings.getServerHost() + ":" + settings.getServerPort();
    }
}
