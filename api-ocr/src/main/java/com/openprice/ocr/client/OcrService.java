package com.openprice.ocr.client;

import org.springframework.web.client.RestTemplate;

import com.openprice.ocr.api.ImageProcessRequest;
import com.openprice.ocr.api.ImageProcessResult;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcrService {

    private final RestTemplate restTemplate;

    @Getter
    private final String url;

    public OcrService(final String url) {
        this(new RestTemplate(), url);
    }

    public OcrService(final RestTemplate restTemplate, final String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public ImageProcessResult processUserReceiptImage(final String imagePath) {
        final ImageProcessRequest request = new ImageProcessRequest(imagePath);
         try{
             return restTemplate.postForObject(url, request, ImageProcessResult.class);
         } catch (Exception ex) {
             log.error("Got error while call OCR RESTful API", ex);
             return new ImageProcessResult(false, null, "Error while call OCR API "+ex.getMessage());
         }
    }
}
