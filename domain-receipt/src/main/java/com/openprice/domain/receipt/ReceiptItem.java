package com.openprice.domain.receipt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Parser parsed receipt items.
 *
 */
@ToString(callSuper=true, exclude={"receiptData"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_item" )
public class ReceiptItem extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_data_id")
    private ReceiptData receiptData;

    @Getter @Setter
    @Column(name="catalog_code")
    private String catalogCode;

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
}
