package com.openprice.ocr;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.common.ApiConstants;
import com.openprice.ocr.api.ImageProcessRequest;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.api.OcrServiceApiUrls;

@RestController
@RequestMapping(value = ApiConstants.INTERNAL_API_ROOT)
public class OcrServiceController {

    @Inject
    OcrProcessor ocrProcessor;

    @RequestMapping(method = RequestMethod.POST, value = OcrServiceApiUrls.URL_OCR_PROCESSOR)
    public HttpEntity<ImageProcessResult> process(@RequestBody final ImageProcessRequest request) {
        try {
            return ResponseEntity.ok(new ImageProcessResult(true, ocrProcessor.processImage(request), null));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ImageProcessResult(false, null, ex.getMessage()));
        }

    }

}
