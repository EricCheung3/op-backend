package com.openprice.ocr;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.openprice.file.FileSystemService;
import com.openprice.ocr.api.ImageProcessRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OcrProcessor {

    private final FileSystemService fileSystemService;
    private final OcrEngine tesseract;

    @Inject
    public OcrProcessor(final FileSystemService fileSystemService,
                        final OcrEngine tesseract) {
        this.fileSystemService = fileSystemService;
        this.tesseract = tesseract;
    }

    public String processImage(final ImageProcessRequest request) {
        log.info("Process image {}.", request.getImageFilePath());

        // TODO return if it is still processing

        // change state to processing

        final Path imageFile = fileSystemService.getReceiptImageFolder().resolve(request.getImageFilePath());

        if (Files.notExists(imageFile)) {
            log.error("Cannot open image file {}, please check file system.", imageFile.toString());
            throw new RuntimeException("Image file not exist: " + imageFile.toString());
        }

        final String ocrResult = tesseract.scan(imageFile.toString());
        // TODO get log output and save to file

        return ocrResult;
    }
}
