package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.UserAccount;
import com.openprice.domain.account.UserAccountRepository;
import com.openprice.domain.account.UserAccountService;
import com.openprice.mail.EmailMessage;
import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.rest.AbstractRestController;
import com.openprice.rest.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegisterRestController extends AbstractRestController {

    private final UserAccountRepository userAccountRepository;
    private final EmailProperties emailProperties;
    private final EmailService emailService;

    @Inject
    public RegisterRestController(final UserAccountRepository userAccountRepository,
                                  final UserAccountService userAccountService,
                                  final EmailProperties emailProperties,
                                  final EmailService emailService) {
        super(userAccountService);
        this.userAccountRepository = userAccountRepository;
        this.emailProperties = emailProperties;
        this.emailService = emailService;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = SiteApiUrls.URL_REGISTRATION_USERS)
    @Transactional
    public HttpEntity<Void> registerNewUser(@RequestBody final RegistrationForm registration)
                                                    throws ResourceNotFoundException {

        log.info("A new user tried to register with username '{}'.", registration.getUsername());
        
        //if user already exist, return 409 conflict
        if (this.userAccountRepository.findByUsername(registration.getUsername()) != null) {
            log.warn("Same username '{}' already registered!", registration.getUsername());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        final UserAccount newUser =
                userAccountService.createUserAccountByRegistrationData(registration.getUsername(),
                                                                       registration.getPassword(),
                                                                       registration.getFirstName(),
                                                                       registration.getLastName(),
                                                                       registration.getEmail());

        sendNewUserRegistered(newUser);
        sendWelcomeEmailToNewUser(newUser);
        log.info("Successfully registered user {} {}", registration.getFirstName(), registration.getLastName());

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    /**
     * Send message to admin when a new user registered by self-register.
     * @param user
     */
    private void sendNewUserRegistered(final UserAccount user) {
        String userEmail = user.getEmail();
        if (StringUtils.isEmpty(userEmail)){
            userEmail = "[not set]";
        }
        final String subject = "New user registered";
        final String message = "A new user registered: name is " + user.getProfile().getDisplayName() + ", email is " + userEmail;
        emailService.sendEmail(new EmailMessage(emailProperties, subject, message, null));
    }

    private void sendWelcomeEmailToNewUser(final UserAccount user) {
        final String subject = "Welcome to OpenPrice";
        final String message = String.format(WELCOME_MESSAGE_TEMPLATE, user.getProfile().getDisplayName(), user.getEmail()); //TODO impl activation feature
        emailService.sendEmail(new EmailMessage(emailProperties, user.getEmail(), user.getProfile().getDisplayName(), subject, message, null));
    }

    private static final String WELCOME_MESSAGE_TEMPLATE = "Hi %s,\n"+
            "Welcome to OpenPrice System. You have registered with this email '%s', " +
            "and enjoy the app! \n" +
            "Sincerely, \n OpenPrice Team\n";

}
