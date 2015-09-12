package com.openprice.ocr;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openprice.common.api.ImageProcessResult;

@Controller
public class OcrServiceController {
    @Inject
    OcrProcessor ocrProcessor;

    // TODO change to health check
    @RequestMapping(method = RequestMethod.GET, value="/", produces = "text/plain")
    @ResponseBody
    String hello() {
        return "Hello!";
    }

    @RequestMapping(method = RequestMethod.GET, value="/process/{userId}")
    @ResponseBody
    public ImageProcessResult process(@PathVariable("userId") final String userId,
                                      @RequestParam("fileName") final String fileName,
                                      @RequestParam("username") final String username) {
        try {
            final String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
            return ocrProcessor.processImage(userId, fileName, decodedUsername);
        } catch (UnsupportedEncodingException ex) {
            // ignore
        }
        return null;
    }


}
