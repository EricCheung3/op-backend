package com.openprice.ocr;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.ocr.api.ImageProcessRequest;
import com.openprice.ocr.api.ImageProcessResult;
import com.openprice.ocr.api.OcrServerApiUrls;

@RestController
public class OcrServiceController {
    @Inject
    OcrProcessor ocrProcessor;

    // TODO change to health check
    @RequestMapping(method = RequestMethod.GET, value="/", produces = "text/plain")
    @ResponseBody
    String hello() {
        return "Hello!";
    }

    @RequestMapping(method = RequestMethod.POST, value = OcrServerApiUrls.URL_OCR_PROCESSOR)
    public HttpEntity<ImageProcessResult> process(@RequestBody final ImageProcessRequest request) {
        ImageProcessResult result = new ImageProcessResult();
        try {
            result.setSuccess(true);
            result.setOcrResult(ocrProcessor.processImage(request));
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMessage(ex.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}
