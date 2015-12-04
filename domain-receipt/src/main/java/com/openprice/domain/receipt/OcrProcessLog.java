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
@Table( name="ocr_process_log" )
public class OcrProcessLog extends BaseEntity {

    /**
     * Use imageId to reference ReceiptImage, in case the image was deleted.
     */
    @Getter @Setter
    @Column(name="image_id", nullable = false)
    private String imageId;

    @Getter @Setter
    @Column(name="owner_name")
    private String ownerName;

    @Getter @Setter
    @Column(name="requester_name")
    private String requesterName;

    @Getter @Setter
    @Column(name="server_name", nullable = false)
    private String serverName;

    @Getter @Setter
    @Column(name="request_time", nullable = false)
    private Long requestTime;

    @Getter @Setter
    @Column(name="start_time", nullable = false)
    private Long startTime;

    @Getter @Setter
    @Column(name="ocr_duration")
    private Long ocrDuration;

    @Getter @Setter
    @Column(name="ocr_result")
    @Lob
    private String ocrResult;

    @Getter @Setter
    @Column(name="error_message")
    private String errorMessage;

}
