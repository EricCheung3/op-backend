package com.openprice.domain.receipt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.openprice.domain.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@SuppressWarnings("serial")
@Entity
@Table
public class ProcessLog extends BaseEntity {

    /**
     * Use imageId to reference ReceiptImage, in case the image was deleted.
     */
    @Getter @Setter
    @Column(nullable = false)
    private String imageId;

    @Getter @Setter
    @Column(nullable = false)
    private String username;

    @Getter @Setter
    @Column(nullable = false)
    private String serverName;

    @Getter @Setter
    @Column(nullable = false)
    private Long startTime;

    @Getter @Setter
    @Column
    private Long ocrDuration;

    @Getter @Setter
    @Column
    @Lob
    private String ocrResult;

    @Getter @Setter
    @Column
    private Long parserDuration; // not used

    @Getter @Setter
    @Column
    @Lob
    private String parserResult; // not used

    @Getter @Setter
    @Column
    private String errorMessage;

}
