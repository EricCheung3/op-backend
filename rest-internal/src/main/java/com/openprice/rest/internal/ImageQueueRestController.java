package com.openprice.rest.internal;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.common.ApiConstants;
import com.openprice.internal.api.ImageQueueRequest;
import com.openprice.internal.api.ImageQueueResult;
import com.openprice.internal.api.InternalServiceApiUrls;
import com.openprice.process.ProcessQueueService;

@RestController
@RequestMapping(value = ApiConstants.INTERNAL_API_ROOT)
public class ImageQueueRestController {

    private final ProcessQueueService queueService;

    @Inject
    public ImageQueueRestController(final ProcessQueueService queueService) {
        this.queueService = queueService;
    }

    @RequestMapping(method = RequestMethod.POST, value = InternalServiceApiUrls.URL_IMAGE_QUEUE)
    public HttpEntity<ImageQueueResult> addImageToQueue(@RequestBody final ImageQueueRequest request) {
        queueService.addImage(request.getImageId(), request.getOwnerId(), request.getRequesterId());
        return ResponseEntity.ok(new ImageQueueResult(true, null));
    }

}
