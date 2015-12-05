package com.openprice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.openprice.common.client.ServiceSettings;

import lombok.Getter;
import lombok.Setter;

@SpringBootApplication
@ConfigurationProperties("application.settings")
public class AdminApiApplicationSettings extends AbstractApplicationSettings {

    @Getter @Setter
    private ServiceSettings internal;

}
