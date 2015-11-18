package com.openprice.domain.account.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Database entity to save user password reset request.
 * <p>The reset password process is:
 * <ul>
 *   <li>User ask for reset password. In Web UI or Mobile App, user can click the button "Forget Password"</li>
 *   <li>Web UI or Mobile App ask user to input email address.</li>
 *   <li>System save a record of <code>UserResetPasswordRequest</code>, and send email to user for reset password link,
 *   with request ID in the link.</li>
 *   <li>User click link in the email to open Reset Password Web page.</li>
 *   <li>Web App call backend API to GET the request with ID. If request expired, return 404. </li>
 *   <li>If request exists, user enter new password.</li>
 *   <li>Web App call backend API to PUT the request with new password, and system reset the password for user. </li>
 * </ul>
 *
 */
@ToString(callSuper=true)
@SuppressWarnings("serial")
@Entity
@Table( name="user_reset_password_request" )
public class UserResetPasswordRequest extends BaseEntity {

    @Getter @Setter
    @Column(name="email", nullable = false)
    private String email;

    @Getter @Setter
    @Column(name="request_time", nullable = false)
    @JsonIgnore
    private LocalDateTime requestTime;

    public static UserResetPasswordRequest createRequest(final String email) {
        final UserResetPasswordRequest request = new UserResetPasswordRequest();
        request.setEmail(email);
        request.setRequestTime(LocalDateTime.now());
        return request;
    }
}
