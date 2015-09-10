package com.openprice.domain.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.account.UserAccount;
import com.openprice.domain.store.Store;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"user", "store"})
@SuppressWarnings("serial")
@Entity
@Table
public class ShoppingItem extends BaseAuditableEntity {
    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_account_id")
    private UserAccount user;

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="store_id")
    private Store store;

    @Getter @Setter
    @Column
    private String itemName;

    @Getter @Setter
    @Column
    private String itemPrice;

}
