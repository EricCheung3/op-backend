package com.openprice;

import com.openprice.file.FileFolderSettings;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractApplicationSettings {

    @Getter @Setter
    private FileFolderSettings file;
}
