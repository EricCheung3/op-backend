package com.openprice.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("application.file")
public class FileFolderSettings {
    @Getter @Setter
    private String imageRootFolder = "/images_test";
    
    @Getter @Setter
    private boolean virtual = true;

}
