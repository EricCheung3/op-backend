package com.openprice.internal.client;

import org.springframework.util.StringUtils;

import com.openprice.common.ApiConstants;
import com.openprice.common.client.AbstractService;
import com.openprice.common.client.ServiceSettings;
import com.openprice.internal.api.ImageQueueRequest;
import com.openprice.internal.api.ImageQueueResult;
import com.openprice.internal.api.InternalServiceApiUrls;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalService extends AbstractService{

    public InternalService(final ServiceSettings settings) {
        super(settings);
    }

    public ImageQueueResult addImageToProcessQueue(final ImageQueueRequest request) {
        if (StringUtils.isEmpty(settings.getServerHost())) {
            log.warn("No Inernal Server configured!");
            return new ImageQueueResult(false, "No server configured.");
        }
        return restTemplate.postForObject(getImageQueueServiceUrl(), request, ImageQueueResult.class);
    }

    private String getImageQueueServiceUrl() {
        return getServiceUrlBase() + ApiConstants.INTERNAL_API_ROOT + InternalServiceApiUrls.URL_IMAGE_QUEUE;
    }
}
