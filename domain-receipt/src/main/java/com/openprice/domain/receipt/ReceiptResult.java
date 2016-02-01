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
 * Parsed receipt result by parser. Parser will recognize store chain first, and saved as <code>chainCode</code>.
 * Then specific store parser will parse receipt items. If chain is not recognized, a generic parser will try to
 * get item list as best effort.
 *
 */
@ToString(callSuper=true, exclude={"receipt"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_result" )
public class ReceiptResult extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne
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

    ReceiptResult() {}

    @Builder(builderMethodName="testObjectBuilder")
    public static ReceiptResult createTestReceiptResult(final Receipt receipt,
                                                        final String chainCode,
                                                        final String branchName,
                                                        final String total,
                                                        final String date) {
        final ReceiptResult receiptResult = new ReceiptResult();
        receiptResult.setReceipt(receipt);
        receiptResult.setChainCode(chainCode);
        receiptResult.setBranchName(branchName);
        receiptResult.setParsedTotal(total);
        receiptResult.setParsedDate(date);
        return receiptResult;
    }

    /**
     * Builder method to create a ReceiptItem from parser result, and add to item list.
     *
     * @param catalogCode
     * @param parsedName
     * @param parsedPrice
     * @return
     */
    ReceiptItem addItem(final String catalogCode, final String parsedName, final String parsedPrice) {
        final ReceiptItem item = new ReceiptItem();
        item.setReceiptResult(this);
        item.setCatalogCode(catalogCode);
        item.setParsedName(parsedName);
        item.setDisplayName(parsedName);
        item.setParsedPrice(parsedPrice);
        item.setDisplayPrice(parsedPrice);
        return item;
    }
}
