package com.openprice.domain.receipt;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.account.user.UserAccount;
import com.openprice.parser.ParsedReceipt;
import com.openprice.parser.ReceiptFieldType;
import com.openprice.parser.data.ValueLine;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Receipt uploaded by user in the server side.
 *
 */
@ToString(callSuper=true, exclude={"user"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt" )
public class Receipt extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_account_id")
    private UserAccount user;

    @Getter @Setter
    @Column(name="receipt_date", nullable=false)
    private LocalDate receiptDate; // default to upload date, and changed to parsed date after successful processing.

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private ReceiptStatusType status;

    @Getter @Setter
    @Column(name="need_feedback")
    private Boolean needFeedback = true;

    Receipt() {}

    Receipt(final UserAccount user) {
        this.user = user;
        this.status = ReceiptStatusType.WAIT_FOR_RESULT;
        this.receiptDate = LocalDate.now();
    }

    @Builder(builderMethodName="testObjectBuilder")
    public static Receipt createTestReceipt(final String id,
                                            final UserAccount user,
                                            final ReceiptStatusType status) {
        final Receipt receipt = new Receipt(user);
        receipt.setId(id);
        if (status != null) {
            receipt.setStatus(status);
        }
        return receipt;
    }

    /**
     * Builder method to create a new receipt image for this receipt with system generated jpg file name.
     *
     * DISCUSS: In the future we may have to change the algorithm of storing image file. Right now we have the
     * limitation to store up to 32768 files under one folder.
     *
     * @return
     */
    public ReceiptImage createImage() {
        final ReceiptImage image = new ReceiptImage();
        image.setReceipt(this);

        // set default value for new image without real content yet
        final SimpleDateFormat dt = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        image.setFileName(dt.format(new Date()) + ".jpg");  // always save as jpg???
        image.setStatus(ProcessStatusType.CREATED);

        return image;
    }

    /**
     * Create a new <code>ReceiptResult</code> object from parser result <code>ParsedReceipt</code>.
     *
     * @param parsedReceipt
     * @return
     */
    public ReceiptResult createReceiptResultFromParserResult(final ParsedReceipt parsedReceipt) {
        final ReceiptResult result = new ReceiptResult();
        result.setReceipt(this);
        if (parsedReceipt.getChain() != null) {
            result.setChainCode(parsedReceipt.getChain().getCode());
        }
        if (parsedReceipt.getBranch() != null) {
            result.setBranchName(parsedReceipt.getBranch().getBranchName());
        }
        final ValueLine parsedTotalValue = parsedReceipt.getFieldToValueMap().get(ReceiptFieldType.Total);
        if (parsedTotalValue != null) {
            result.setParsedTotal(parsedTotalValue.getValue());
        }
        final ValueLine parsedDateValue = parsedReceipt.getFieldToValueMap().get(ReceiptFieldType.Date);
        if (parsedDateValue != null) {
            result.setParsedDate(parsedDateValue.getValue());
        }
        return result;
    }
}
