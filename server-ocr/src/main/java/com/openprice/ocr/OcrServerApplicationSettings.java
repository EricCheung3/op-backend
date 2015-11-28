package com.openprice.ocr;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.openprice.file.FileFolderSettings;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.settings")
public class OcrServerApplicationSettings {

    @Getter @Setter
    private FileFolderSettings file;
}
