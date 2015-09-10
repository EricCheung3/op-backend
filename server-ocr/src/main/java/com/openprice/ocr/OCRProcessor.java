package com.openprice.ocr;

import java.nio.file.Files;
import java.nio.file.Path;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.openprice.common.api.ImageProcessResult;
import com.openprice.file.FileSystemService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OCRProcessor {

    private final FileSystemService fileSystemService;
    private final Tesseract tesseract;

    @Inject
    public OCRProcessor(final FileSystemService fileSystemService,
                        final Tesseract tesseract) {
        this.fileSystemService = fileSystemService;
        this.tesseract = tesseract;
    }

    public ImageProcessResult processImage(final String username, final String fileName) {
        log.info("Process image {} from user '{}'.", fileName, username);

        // TODO return if it is still processing

        // change state to processing

        final String imagePath = username + fileSystemService.getPathSeparator() + fileName;
        final Path imageFile = fileSystemService.getImageRootFolder().resolve(imagePath);

        if (Files.notExists(imageFile)) {
            log.error("Cannot open image file at "+imageFile.toString());
            return null;  // TODO throw exception?
        }

        final String ocrResult = tesseract.scan(imageFile.toString());
        // TODO get log output and save to file


        final ImageProcessResult result = new ImageProcessResult();
        result.setUsername(username);
        result.setFileName(fileName);
        result.setOcrResult(ocrResult);
        return result;
    }
}
