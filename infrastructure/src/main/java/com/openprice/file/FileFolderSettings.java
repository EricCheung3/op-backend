package com.openprice.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileFolderSettings {

    private String imageRootFolder = "/images_test";

    private boolean virtual = true;

}
