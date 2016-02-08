package com.openprice.domain.receipt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.parser.ReceiptFieldType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Parser parsed receipt field value.
 * This is for parser result investigation purpose, won't present to end user.
 */
@ToString(callSuper=true, exclude={"receiptResult"})
@SuppressWarnings("serial")
@Entity
@Table( name="receipt_field" )
public class ReceiptField extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="receipt_result_id")
    @org.hibernate.annotations.OnDelete(
        action = org.hibernate.annotations.OnDeleteAction.CASCADE
    )
    private ReceiptResult receiptResult;

    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false)
    private ReceiptFieldType type;

    @Getter @Setter
    @Column(name="value", nullable=false)
    private String value;

    @Getter @Setter
    @Column(name="line_number", nullable=false)
    private Integer lineNumber;

    ReceiptField() {}

    @Builder(builderMethodName="testObjectBuilder")
    public static ReceiptField createTestReceiptField(final String id,
                                                      final ReceiptResult receiptResult,
                                                      final ReceiptFieldType type,
                                                      final String value,
                                                      final Integer lineNumber) {
        final ReceiptField field = new ReceiptField();
        field.setId(id);
        field.setReceiptResult(receiptResult);
        field.setType(type);
        field.setValue(value);
        field.setLineNumber(lineNumber);
        return field;
    }
}
