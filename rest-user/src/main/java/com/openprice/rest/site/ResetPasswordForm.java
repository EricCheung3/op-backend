package com.openprice.rest.site;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("serial")
public class ResetPasswordForm implements Serializable {

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String newPassword;
}
