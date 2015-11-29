package com.openprice;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.openprice.process.ProcessSettings;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.settings")
public class InternalApiApplicationSettings extends AbstractApplicationSettings {

    @Getter @Setter
    private ProcessSettings process;
}
