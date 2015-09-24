package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.common.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@SuppressWarnings("serial")
@Entity
@Table( name="store_branch" )
public class StoreBranch extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @Getter @Setter
    @Column(name="name", nullable=false)
    private String name;

    @Getter @Setter
    @Column(name="phone", nullable=false)
    private String phone;

    @Getter @Setter
    @Column(name="gst_number", nullable=false)
    private String gstNumber;

    @Getter @Setter
    @Column(name="slogan", nullable=false)
    private String slogan;

    @Setter
    @Embedded
    private Address address;

    /**
     * Because Hibernate will set address to null if all properties of address are null,
     * we have to handle it by setting a new value object.
     * @return
     */
    public Address getAddress() {
        if (address == null) {
            address = new Address();
        }
        return address;
    }

}
