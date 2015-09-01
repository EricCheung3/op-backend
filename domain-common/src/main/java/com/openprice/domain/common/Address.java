package com.openprice.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Address implements Serializable {
    
    @Getter @Setter
    @Column
    private String address1;
    
    @Getter @Setter
    @Column
    private String address2;
    
    @Getter @Setter
    @Column
    private String city;
    
    @Getter @Setter
    @Column
    private String state;
    
    @Getter @Setter
    @Column
    private String zip;
    
    @Getter @Setter
    @Column
    private String country;
}

