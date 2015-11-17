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

}
