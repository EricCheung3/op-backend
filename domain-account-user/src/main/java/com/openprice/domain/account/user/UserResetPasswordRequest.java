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
