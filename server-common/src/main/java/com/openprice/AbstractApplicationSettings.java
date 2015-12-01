package com.openprice;

import com.openprice.file.FileFolderSettings;
import com.openprice.mail.EmailProperties;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractApplicationSettings {

    @Getter @Setter
    private FileFolderSettings file;

    @Getter @Setter
    private EmailProperties email;
}
