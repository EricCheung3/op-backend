package com.openprice.rest.user.receipt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=true, exclude={"base64String"})
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("serial")
public class ImageDataForm implements Serializable {

    private String base64String;

}
