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

@ToString(callSuper=true, exclude={"receipt", "ocrResult"})
@SuppressWarnings("serial")
@Entity
@Table
public class ReceiptImage extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_id")
    private Receipt receipt;

    @Getter @Setter
    @Column
    private String fileName;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private ProcessStatusType status;

    @Getter @Setter
    @Column
    @Lob
    private String ocrResult;

}
