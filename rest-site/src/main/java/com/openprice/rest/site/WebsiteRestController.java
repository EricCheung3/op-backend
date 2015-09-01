package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.UserAccountService;
import com.openprice.rest.AbstractRestController;

@RestController
public class WebsiteRestController extends AbstractRestController {
    private final WebsiteResourceAssembler websiteResourceAssembler;

    @Inject
    public WebsiteRestController(final UserAccountService userAccountService,
                                 final WebsiteResourceAssembler websiteResourceAssembler) {
        super(userAccountService);
        this.websiteResourceAssembler = websiteResourceAssembler;
    }

    /**
     * Get public website resource root, with links to other resources
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<WebsiteResource> getPublicWebsiteResource() {
        WebsiteResource resource = websiteResourceAssembler.toResource();
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
