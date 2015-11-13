package com.openprice.domain.receipt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openprice.domain.BaseAuditableEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(callSuper=true, exclude={"receipt", "items"})
@SuppressWarnings("serial")
@Entity
@Table( name="parser_result" )
public class ParserResult extends BaseAuditableEntity {

    @Getter @Setter
    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="receipt_id")
    private Receipt receipt;

    @Getter @Setter
    @Column(name="chain_code")
    private String chainCode;

    @Getter @Setter
    @Column(name="branch_name")
    private String branchName;

    @Getter @Setter
    @Column(name="parsed_total")
    private String parsedTotal;

    @Getter @Setter
    @Column(name="parsed_date")
    private String parsedDate;

    @Getter @Setter
    @JsonIgnore
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL, mappedBy="result")
    //@OrderBy("createdTime")
    private List<ReceiptItem> items = new ArrayList<>();
}
