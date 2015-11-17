package com.openprice.domain.receipt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;
import com.openprice.domain.account.user.UserAccount;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Receipt uploaded by user in the server side.
 *
 */
@ToString(callSuper=true, exclude={"user", "images", "results"})
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
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="receipt")
    @OrderBy("createdTime")
    private List<ReceiptImage> images = new ArrayList<>();

    @Getter @Setter
    @Column(name="rating")
    private Integer rating;

    @Getter @Setter
    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="receipt")
    @OrderBy("createdTime")
    private List<ReceiptData> results = new ArrayList<>();

    /**
     * Builder method to create a new receipt.
     *
     * @param user
     * @return
     */
    public static Receipt createReceipt(final UserAccount user) {
        final Receipt receipt = new Receipt();
        receipt.setUser(user);
        return receipt;
    }

    /**
     * Builder method to create a new receipt image for this receipt.
     *
     * @return
     */
    public ReceiptImage createImage() {
        final ReceiptImage image = new ReceiptImage();
        image.setReceipt(this);
        images.add(image);

        // set default value for new image without real content yet
        final SimpleDateFormat dt = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
        image.setFileName(dt.format(new Date()) + ".jpg");  // always save as jpg?
        image.setStatus(ProcessStatusType.CREATED);

        return image;
    }
}
