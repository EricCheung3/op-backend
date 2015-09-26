package com.openprice.rest.user.receipt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown=true)
@Builder
@NoArgsConstructor @AllArgsConstructor
@Data
@ToString(callSuper=true, exclude={"base64String"})
public class ImageDataForm {
    private String base64String;

}
