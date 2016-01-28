package com.openprice.ocr.client;

import com.openprice.common.ApiConstants;
import com.openprice.common.client.AbstractService;
import com.openprice.common.client.ServiceSettings;
import com.openprice.ocr.api.ImageProcessRequest;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.api.OcrServiceApiUrls;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OcrService extends AbstractService {

    public OcrService(final ServiceSettings settings) {
        super(settings);
    }

    public ImageProcessResult processUserReceiptImage(final String imagePath) {
        final ImageProcessRequest request = new ImageProcessRequest(imagePath);
         try{
             return restTemplate.postForObject(getOcrServiceUrl(), request, ImageProcessResult.class);
         } catch (Exception ex) {
             log.error("SEVERE: Got error while call OCR RESTful API", ex);
             return new ImageProcessResult(false, null, "Error while call OCR API "+ex.getMessage());
         }
    }

    public String getOcrServiceUrl() {
        return getServiceUrlBase() + ApiConstants.INTERNAL_API_ROOT + OcrServiceApiUrls.URL_OCR_PROCESSOR;
    }

}
