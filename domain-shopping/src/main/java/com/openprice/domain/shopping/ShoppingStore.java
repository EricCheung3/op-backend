package com.openprice.domain.shopping;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>User shopping list associated with a store.
 *
 * <p><b>Notes:</b> TODO: It may not have store associated, so it is a generic shopping list with a name.
 */
@ToString(callSuper=true, exclude={"user", "items"})
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

    @Getter @Setter
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="store")
    @OrderBy("name")
    private List<ShoppingItem> items = new ArrayList<>();

}
