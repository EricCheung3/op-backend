package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.rest.AbstractExternalRestController;

/**
 * REST API Controller for public website.
 *
 */
@RestController
public class WebsiteRestController extends AbstractExternalRestController {

    private final WebsiteResourceAssembler websiteResourceAssembler;

    @Inject
    public WebsiteRestController(final WebsiteResourceAssembler websiteResourceAssembler) {
        this.websiteResourceAssembler = websiteResourceAssembler;
    }

    /**
     * Get public website resource root, with links to other resources
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<WebsiteResource> getPublicWebsiteResource() {
        return new ResponseEntity<>(websiteResourceAssembler.toResource(), HttpStatus.OK);
    }

}
