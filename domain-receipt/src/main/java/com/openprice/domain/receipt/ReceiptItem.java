package com.openprice.domain.receipt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Parser parsed receipt items. User can edit display name and display price to fix parser result,
 * or user can delete the item, which will be set <code>ignore=true</code>, and won't show up in receipt item list
 * to user.
 *
 */
@ToString(callSuper=true, exclude={"receiptResult"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_item" )
public class ReceiptItem extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="receipt_result_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private ReceiptResult receiptResult;

    @Getter @Setter
    @JsonIgnore // we don't expose this to user
    @Column(name="line_number")
    private Integer lineNumber;

    @Getter @Setter
    @Column(name="catalog_code")
    private String catalogCode; // optional

    // TODO save category code
//    @Getter @Setter
//    @Column(name="product_category")
//    private String categoryCode;

    @Getter @Setter
    @Column(name="parsed_name")
    private String parsedName;

    @Getter @Setter
    @Column(name="display_name")
    private String displayName;

    @Getter @Setter
    @Column(name="parsed_price")
    private String parsedPrice;

    @Getter @Setter
    @Column(name="display_price")
    private String displayPrice;

    @Getter @Setter
    @JsonIgnore // we don't expose this to user
    @Column(name="user_ignored")
    private Boolean ignored = false;

    ReceiptItem() {}

    @Builder(builderMethodName="testObjectBuilder")
    public static ReceiptItem createTestReceiptItem(final String id,
                                                    final ReceiptResult receiptResult,
                                                    final Integer lineNumber,
                                                    final String catalogCode,
                                                    final String parsedName,
                                                    final String displayName,
                                                    final String parsedPrice,
                                                    final String displayPrice) {
        final ReceiptItem item = new ReceiptItem();
        item.setId(id);
        item.setReceiptResult(receiptResult);
        item.setLineNumber(lineNumber);
        item.setCatalogCode(catalogCode);
        item.setParsedName(parsedName);
        item.setDisplayName(displayName);
        item.setParsedPrice(parsedPrice);
        item.setDisplayPrice(displayPrice);
        return item;
    }

}
