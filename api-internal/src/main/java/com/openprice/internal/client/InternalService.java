package com.openprice.internal.client;

import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.openprice.internal.api.ImageQueueRequest;
import com.openprice.internal.api.ImageQueueResult;
import com.openprice.internal.api.InternalServiceApiUrls;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalService {

    private final RestTemplate restTemplate;
    private final InternalServiceSettings settings;

    public InternalService(final InternalServiceSettings settings) {
        this.restTemplate = new RestTemplate();
        this.settings = settings;
    }

    public ImageQueueResult addImageToProcessQueue(final String imageId, final String ownerId, final String requesterId) {
        if (StringUtils.isEmpty(settings.getServerHost())) {
            log.warn("No Inernal Server configured!");
            return new ImageQueueResult(false, "No server configured.");
        }
        return restTemplate.postForObject(getImageQueueServiceUrl(), new ImageQueueRequest(imageId, ownerId, requesterId), ImageQueueResult.class);
    }

    private String getImageQueueServiceUrl() {
        return "http://" + settings.getServerHost() + ":" + settings.getServerPort() + InternalServiceApiUrls.URL_IMAGE_QUEUE;
    }
}
