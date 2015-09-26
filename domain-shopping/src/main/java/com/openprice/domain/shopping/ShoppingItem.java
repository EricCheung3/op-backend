package com.openprice.domain.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.domain.store.StoreChain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"user", "store"})
@SuppressWarnings("serial")
@Entity
@Table( name="shopping_item" )
public class ShoppingItem extends BaseAuditableEntity {
    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_account_id")
    private UserAccount user;

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chain_id")
    private StoreChain store;

    @Getter @Setter
    @Column(name="item_name")
    private String itemName;

    @Getter @Setter
    @Column(name="item_price")
    private String itemPrice;

}
