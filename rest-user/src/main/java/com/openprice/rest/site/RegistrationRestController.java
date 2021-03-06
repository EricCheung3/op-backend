package com.openprice.rest.site;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.account.user.UserAccountRepository;
import com.openprice.domain.account.user.UserAccountService;
import com.openprice.domain.account.user.UserResetPasswordRequest;
import com.openprice.mail.EmailMessage;
import com.openprice.mail.EmailProperties;
import com.openprice.mail.EmailService;
import com.openprice.rest.AbstractExternalRestController;
import com.openprice.rest.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * REST API Controller for user registration related tasks.
 *
 */
@RestController
@Slf4j
public class RegistrationRestController extends AbstractExternalRestController implements SiteApiUrls {

    public static final String RESET_PASSWORD_PATH = "/reset-password/";

    private final UserAccountService userAccountService;
    private final UserAccountRepository userAccountRepository;
    private final EmailProperties emailProperties;
    private final EmailService emailService;

    @Inject
    public RegistrationRestController(final UserAccountService userAccountService,
                                      final UserAccountRepository userAccountRepository,
                                      final EmailProperties emailProperties,
                                      final EmailService emailService) {
        this.userAccountService = userAccountService;
        this.userAccountRepository = userAccountRepository;
        this.emailProperties = emailProperties;
        this.emailService = emailService;
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_PUBLIC_REGISTRATION)
    @Transactional
    public HttpEntity<Void> registerNewUser(@RequestBody final RegistrationForm registration)
                throws ResourceNotFoundException {

        log.info("A new user tried to register with email '{}'.", registration.getEmail());

        //if user already exist, return 409 conflict
        if (this.userAccountRepository.findByEmail(registration.getEmail()) != null) {
            log.warn("Same user <{}> already registered!", registration.getEmail());
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        final UserAccount newUser =
                userAccountService.createUserAccountByRegistrationData(registration.getEmail(),
                                                                       registration.getPassword(),
                                                                       registration.getFirstName(),
                                                                       registration.getLastName());

        sendNewUserRegistered(newUser);
        sendWelcomeEmailToNewUser(newUser);
        log.info("Successfully registered user '{} {}' with email <{}>",
                registration.getFirstName(), registration.getLastName(), registration.getEmail());

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_PUBLIC_RESET_PASSWORD_REQUESTS)
    @Transactional
    public HttpEntity<Void> forgetPassword(@RequestBody final ForgetPasswordForm form)
                throws ResourceNotFoundException {
        final String email = form.getEmail();
        log.info("User <{}> tried to reset password.", email);
        final UserResetPasswordRequest request = userAccountService.createResetPasswordRequest(email);

        if (request == null) {
            throw new ResourceNotFoundException("No registered user with email "+email);
        }

        sendResetPasswordLinkToUser(userAccountRepository.findByEmail(email), request);
        return ResponseEntity.accepted().body(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST)
    @Transactional
    public HttpEntity<UserResetPasswordRequest> getResetPasswordRequest(
            @PathVariable("requestId") final String requestId) {
        final UserResetPasswordRequest request = userAccountService.getUserResetPasswordReqest(requestId);
        if (request == null) {
            throw new ResourceNotFoundException("No such reset password request.");
        }
        return ResponseEntity.ok(request);
    }

    @RequestMapping(method = RequestMethod.PUT, value = URL_PUBLIC_RESET_PASSWORD_REQUESTS_REQUEST)
    @Transactional
    public HttpEntity<Void> resetPassword(
            @PathVariable("requestId") final String requestId,
            @RequestBody final ResetPasswordForm form) throws ResourceNotFoundException {
        final UserResetPasswordRequest request = userAccountService.getUserResetPasswordReqest(requestId);
        if (request == null) {
            throw new ResourceNotFoundException("No such reset password request.");
        }
        userAccountService.resetPasswordWithRequest(requestId, request.getEmail(), form.getNewPassword());
        log.info("User <{}> successfully reset password.", request.getEmail());
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
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
        emailService.sendEmail(EmailMessage.createEmailToAdmin(emailProperties, subject, message, null));
    }

    private void sendWelcomeEmailToNewUser(final UserAccount user) {
        final String subject = "Welcome to Openprice";
        final String message = String.format(WELCOME_MESSAGE_EMAIL_TEMPLATE, user.getProfile().getDisplayName(), user.getEmail()); //TODO impl activation feature
        emailService.sendEmail(EmailMessage.createEmail(emailProperties, user.getEmail(), user.getProfile().getDisplayName(), subject, message, null));
    }

    private void sendResetPasswordLinkToUser(final UserAccount user, final UserResetPasswordRequest request) {
        final String url = emailProperties.getWebServerUrl() + RESET_PASSWORD_PATH + request.getId();
        final String subject = "Reset Password in Openprice";
        final String message = String.format(RESET_PASSWORD_EMAIL_TEMPLATE, user.getProfile().getDisplayName(), url);
        emailService.sendEmail(EmailMessage.createEmail(emailProperties, user.getEmail(), user.getProfile().getDisplayName(), subject, message, null));
    }

    private static final String WELCOME_MESSAGE_EMAIL_TEMPLATE = "Dear %s,\n"+
            "Welcome to Openprice. You, or someone on your behalf have registered with the email '%s'.\n" +
            "Enjoy the app! \n\n" +
            "Sincerely, \n Openprice Team\n";

    private static final String RESET_PASSWORD_EMAIL_TEMPLATE = "Dear %s, \n" +
            "You have requested to reset your password. Please click following url to reset your password:\n" +
            " %s\n This link will be expired after two hours.\n" +
            "If you didn't request password reset, please ignore this email.\n\n" +
            "Sincerely, \n Openprice Team\n";
}
