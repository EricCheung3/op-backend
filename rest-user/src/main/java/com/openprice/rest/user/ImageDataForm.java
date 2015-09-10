package com.openprice.rest.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"base64String"})
public class ImageDataForm {

    @Getter @Setter
    private String base64String;

}
