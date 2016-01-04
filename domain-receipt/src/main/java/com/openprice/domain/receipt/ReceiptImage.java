package com.openprice.domain.receipt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User uploaded receipt images through mobile device or Web UI. It will be scanned by OCR service
 * and store OCR result in database.
 *
 */
@ToString(callSuper=true, exclude={"receipt", "ocrResult"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_image" )
public class ReceiptImage extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private Receipt receipt;

    @Getter @Setter
    @Column(name="file_name")
    private String fileName;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private ProcessStatusType status;

    @Getter @Setter
    @Column(name="ocr_result")
    @Lob
    private String ocrResult;

    ReceiptImage() {}

    @Builder(builderMethodName="testObjectBuilder")
    public static ReceiptImage createTestReceiptImage(final String id,
                                                      final Receipt receipt,
                                                      final String fileName,
                                                      final ProcessStatusType status,
                                                      final String ocrResult) {
        final ReceiptImage image = new ReceiptImage();
        image.setId(id);
        image.setReceipt(receipt);
        image.setFileName(fileName);
        image.setStatus(status);
        image.setOcrResult(ocrResult);
        return image;
    }

}
