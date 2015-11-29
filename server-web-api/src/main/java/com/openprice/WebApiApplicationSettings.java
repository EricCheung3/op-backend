package com.openprice;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.openprice.common.client.ServiceSettings;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.settings")
public class WebApiApplicationSettings extends AbstractApplicationSettings {

    @Getter @Setter
    private ServiceSettings internal;
}
