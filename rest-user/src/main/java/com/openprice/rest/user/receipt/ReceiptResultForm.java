package com.openprice.rest.user.receipt;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openprice.domain.receipt.ReceiptResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
@SuppressWarnings("serial")
public class ReceiptResultForm implements Serializable {

    private String date;

    private String totalPrice;

    public ReceiptResult updateReceiptResult(ReceiptResult result) {
        result.setParsedDate(date);
        result.setParsedTotal(totalPrice);
        return result;
    }
}
