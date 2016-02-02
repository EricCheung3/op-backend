package com.openprice.domain.shopping;

import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 * <p>User shopping list associated with a store.
 *
 * <p><b>Notes:</b> TODO: It may not have store associated, so it is a generic shopping list with a name.
 */
@ToString(callSuper=true, exclude={"user"})
@SuppressWarnings("serial")
@Entity
@Table( name="shopping_store" )
public class ShoppingStore extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne
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

    ShoppingStore() {}

    /**
     * Builder method to create <code>ShoppingStore</code> object from a specific <code>StoreChain</code> object.
     *
     * @param chain
     * @return
     */
    public static ShoppingStore createShoppingStore(final UserAccount user, final StoreChain chain) {
        final ShoppingStore store = new ShoppingStore();
        store.setUser(user);
        store.setChainCode(chain.getCode());
        store.setDisplayName(chain.getName());
        return store;
    }

    /**
     * Builder method to create generic <code>ShoppingStore</code> object with a display name.
     *
     * @param name
     * @return
     */
    public static ShoppingStore createGenericShoppingStore(final UserAccount user, final String name) {
        final ShoppingStore store = new ShoppingStore();
        store.setUser(user);
        store.setDisplayName(name);
        return store;
    }
}
