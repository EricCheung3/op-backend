CREATE TABLE receipt_result (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    receipt_id VARCHAR(50) NOT NULL,
    chain_code VARCHAR(255),
    branch_name VARCHAR(255),
    parsed_total VARCHAR(255),
    parsed_date VARCHAR(255),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_receipt_result_1
    ON receipt_result(receipt_id ASC);
    
ALTER TABLE receipt_result
    ADD CONSTRAINT fk_receipt_result_receipt
    FOREIGN KEY (receipt_id)
    REFERENCES receipt(id)
    ON DELETE CASCADE;

CREATE TABLE receipt_item (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    receipt_result_id VARCHAR(50) NOT NULL,
    catalog_code VARCHAR(255),
    line_number INTEGER,
    parsed_name VARCHAR(255),
    display_name VARCHAR(255),
    parsed_price VARCHAR(255),
    display_price VARCHAR(255),
    user_ignored BOOLEAN,
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_receipt_item_1
    ON receipt_item(receipt_result_id ASC);
    
ALTER TABLE receipt_item
    ADD CONSTRAINT fk_receipt_item_receipt_data
    FOREIGN KEY (receipt_result_id)
    REFERENCES receipt_result(id)
    ON DELETE CASCADE;
