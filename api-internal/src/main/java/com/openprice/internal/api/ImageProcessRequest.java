package com.openprice.ocr.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageProcessRequest {
    private String userId;

    private String username;

    private String fileName;

}
