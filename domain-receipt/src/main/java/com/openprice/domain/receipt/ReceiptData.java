package com.openprice.domain.receipt;

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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Parsed receipt data by parser. Parser will recognize store chain first, and saved as <code>chainCode</code>.
 * Then specific store parser will parse receipt items. If chain is not recognized, a generic parser will try to
 * get item list as best effort.
 *
 */
@ToString(callSuper=true, exclude={"receipt", "items"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_data" )
public class ReceiptData extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private Receipt receipt;

    @Getter @Setter
    @Column(name="chain_code")
    private String chainCode;

    @Getter @Setter
    @Column(name="branch_name")
    private String branchName;

    @Getter @Setter
    @Column(name="parsed_total")
    private String parsedTotal;

    @Getter @Setter
    @Column(name="parsed_date")
    private String parsedDate;

    @Getter @Setter
    @JsonIgnore
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL, mappedBy="receiptData")
    @OrderBy("lineNumber ASC")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private List<ReceiptItem> items = new ArrayList<>();

    ReceiptData() {}

    /**
     * Builder method to create a ReceiptItem from parser result data, and add to item list.
     *
     * @param catalogCode
     * @param parsedName
     * @param parsedPrice
     * @return
     */
    public ReceiptItem addItem(final String catalogCode, final String parsedName, final String parsedPrice) {
        final ReceiptItem item = new ReceiptItem();
        item.setReceiptData(this);
        item.setCatalogCode(catalogCode);
        item.setParsedName(parsedName);
        item.setDisplayName(parsedName);
        item.setParsedPrice(parsedPrice);
        item.setDisplayPrice(parsedPrice);
        items.add(item);
        return item;
    }
}
