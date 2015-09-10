package com.openprice.domain.receipt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * We don't persist receipt item to database now.
 *
 */
@ToString(callSuper=true)
public class ReceiptItem {

    @Getter @Setter
    private String name; //item name

    @Getter @Setter
    private String buyPrice;//price spent on the item; can be a unit price or the price of several units (kg)

    @Getter @Setter
    private String unitPrice;//unit price of the item (usually in kilogram)

    @Getter @Setter
    private String weight;//weight of the item bought

    @Getter @Setter
    private String category;//category of the item

    @Getter @Setter
    private String regPrice;//regular price

    @Getter @Setter
    private String saving;//savings

}
