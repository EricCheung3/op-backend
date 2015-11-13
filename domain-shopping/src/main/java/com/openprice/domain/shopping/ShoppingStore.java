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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"user"})
@SuppressWarnings("serial")
@Entity
@Table( name="shopping_store" )
public class ShoppingStore extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_account_id")
    private UserAccount user;

    /**
     * Connected Store Chain code. It might be null, so no store associated.
     *
     * we don't want to reference StoreChain, but to use soft link with chain code.
     */
    @Getter @Setter
    @Column(name="chain_code")
    private String chainCode;

    /**
     * Copied from Store Chain name, user can edit?
     */
    @Getter @Setter
    @Column(name="display_name")
    private String displayName;

}
