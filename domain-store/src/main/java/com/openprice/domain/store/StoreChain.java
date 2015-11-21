package com.openprice.domain.store;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@ToString(callSuper=true, exclude={"branches"})
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

    @Getter @Setter
    @Column(name="categories")
    private String categories;

    @Getter @Setter
    @Column(name="identify_fields")
    private String identifyFields;

    @Getter @Setter
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="chain")
    private List<StoreBranch> branches = new ArrayList<>();

    @Getter @Setter
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="chain")
    private List<Catalog> catalogs = new ArrayList<>();

    StoreChain() {}

    /**
     * Builder method to create a StoreChain with code and name.
     *
     * @param code
     * @param name
     * @return
     */
    public static StoreChain createStoreChain(final String code, final String name) {
        final StoreChain chain = new StoreChain();
        chain.setCode(code);
        chain.setName(name);
        return chain;
    }

    /**
     * Builder method to create a StoreBranch and add to branches.
     *
     * @param name
     * @return
     */
    public StoreBranch addBranch(final String name) {
        final StoreBranch branch = new StoreBranch();
        branch.setChain(this);
        branch.setName(name);
        branches.add(branch);
        return branch;
    }

    /**
     * Builder method to create a Catalog and add to catalogs.
     *
     * @param name
     * @return
     */
    public Catalog addCatalog(final String code,
                              final String name,
                              final String number,
                              final String category,
                              final String price,
                              final String naturalName,
                              final String labelCodes) {
        final Catalog catalog = new Catalog();
        catalog.setChain(this);
        catalog.setCode(code);
        catalog.setName(name);
        catalog.setNumber(number);
        catalog.setCategory(category);
        catalog.setPrice(price);
        catalog.setNaturalName(naturalName);
        catalog.setLabelCodes(labelCodes);
        catalogs.add(catalog);
        return catalog;
    }
}
