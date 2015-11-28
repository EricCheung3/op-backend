package com.openprice;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.openprice.file.FileFolderSettings;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.settings")
public abstract class AbstractApplicationSettings {

    @Getter @Setter
    private FileFolderSettings file;
}
