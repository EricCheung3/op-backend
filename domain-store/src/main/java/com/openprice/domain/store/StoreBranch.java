package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.common.Address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Ground truth data for store branch, like name, address, phone number, GST number, etc.
 *
 */
@ToString(callSuper=true, exclude={"chain"})
@SuppressWarnings("serial")
@Entity
@Table( name="store_branch" )
public class StoreBranch extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chain_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private StoreChain chain;

    @Getter @Setter
    @Column(name="name", nullable=false)
    private String name;

    @Getter @Setter
    @Column(name="phone")
    private String phone;

    @Getter @Setter
    @Column(name="gst_number")
    private String gstNumber;

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

    StoreBranch() {}
}
