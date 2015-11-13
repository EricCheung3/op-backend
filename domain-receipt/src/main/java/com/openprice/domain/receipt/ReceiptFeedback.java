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

@ToString(callSuper=true, exclude={"receipt"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_feedback" )
public class ReceiptFeedback extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_id")
    private Receipt receipt;

    @Getter @Setter
    @Column(name="rating")
    private Integer rating;

    @Getter @Setter
    @Column(name="comment")
    private String comment;

}
