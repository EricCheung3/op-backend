CREATE TABLE receipt (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    user_account_id VARCHAR(50) NOT NULL,
    rating SMALLINT,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_receipt_1
    ON receipt(user_account_id ASC);
    
ALTER TABLE receipt
    ADD CONSTRAINT fk_receipt_user_account
    FOREIGN KEY (user_account_id)
    REFERENCES user_account(id)
    ON DELETE CASCADE;

CREATE TABLE receipt_image (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    receipt_id VARCHAR(50) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    ocr_result LONGTEXT,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_receipt_image_1
    ON receipt_image(receipt_id ASC);
    
ALTER TABLE receipt_image
    ADD CONSTRAINT fk_receipt_image_receipt
    FOREIGN KEY (receipt_id)
    REFERENCES receipt(id)
    ON DELETE CASCADE;
