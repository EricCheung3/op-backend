package com.openprice.rest.site;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
public class RegistrationForm implements Serializable {

    private String firstName;

    private String lastName;

    private String email;

    private String password;
}
