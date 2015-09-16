package com.openprice.domain.common;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
@Data
public class Address implements Serializable {

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String zip;

    private String country;
}
