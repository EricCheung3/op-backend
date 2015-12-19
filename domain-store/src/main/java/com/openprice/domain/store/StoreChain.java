package com.openprice.domain.store;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Basic data for franchising stores, like Safeway or Superstore.
 *
 * <p><b>Notes:</b> Parser will have store chain and branch config data as text files. The chain and branch data
 * in database is mostly for user shopping list usage right now.
 *
 */
@ToString(callSuper=true)
@SuppressWarnings("serial")
@Entity
@Table( name="store_chain" )
public class StoreChain extends BaseAuditableEntity {

    @Getter @Setter
    @Column(name="code", unique=true, nullable=false)
    private String code;

    @Getter @Setter
    @Column(name="name", nullable=false)
    private String name;

    StoreChain() {}

    /**
     * Builder method to create a StoreChain with code and name. Should be only used by test code!
     *
     * @param code
     * @param name
     * @return
     */
    public static StoreChain createTestStoreChain(final String id, final String code, final String name) {
        final StoreChain chain = new StoreChain();
        chain.setId(id);
        chain.setCode(code);
        chain.setName(name);
        return chain;
    }
}
