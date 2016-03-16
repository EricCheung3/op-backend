package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.mail.EmailMessage;
import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.rest.AbstractExternalRestController;

/**
 * REST API Controller for public website.
 *
 */
@RestController
public class WebsiteRestController extends AbstractExternalRestController implements SiteApiUrls {

    private final WebsiteResource.Assembler websiteResourceAssembler;
    private final EmailService emailService;
    private final EmailProperties emailProperties;

    @Inject
    public WebsiteRestController(final WebsiteResource.Assembler websiteResourceAssembler,
                                 final EmailService emailService,
                                 final EmailProperties emailProperties) {
        this.websiteResourceAssembler = websiteResourceAssembler;
        this.emailService = emailService;
        this.emailProperties = emailProperties;
    }

    /**
     * Get public website resource root, with links to other resources
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public HttpEntity<WebsiteResource> getPublicWebsiteResource() {
        return new ResponseEntity<>(websiteResourceAssembler.toResource(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_PUBLIC_CONTACT)
    public HttpEntity<Void> sendContactMessage(@RequestBody final ContactForm contactForm) {
        String subject = "Contact message from " + contactForm.getName();
        String message = contactForm.getName() + " tried to contact you on your website: \n" + contactForm.getMessage();
        emailService.sendEmail(
                EmailMessage.createEmailToAdmin(emailProperties, subject, message));
        return ResponseEntity.ok().build();
    }

}
