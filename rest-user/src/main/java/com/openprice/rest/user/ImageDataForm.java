package com.openprice.rest.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@ToString(callSuper=true, exclude={"base64String"})
public class ImageDataForm {

    @Getter @Setter
    private String base64String;

}
