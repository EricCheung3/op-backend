CREATE TABLE receipt (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    user_account_id VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    receipt_date DATE NOT NULL,
    need_feedback BOOLEAN,

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

CREATE TABLE receipt_feedback (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    receipt_id VARCHAR(50) NOT NULL,
    rating INTEGER,
    comment VARCHAR(2048),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_receipt_feedback_1
    ON receipt_feedback(receipt_id ASC);
    
ALTER TABLE receipt_feedback
    ADD CONSTRAINT fk_receipt_feedback_receipt
    FOREIGN KEY (receipt_id)
    REFERENCES receipt(id)
    ON DELETE CASCADE;

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


CREATE TABLE ocr_process_log (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,

    image_id VARCHAR(50) NOT NULL,
    owner_name VARCHAR(100),
    requester_name VARCHAR(100),
    server_name VARCHAR(255) NOT NULL,
    request_time BIGINT NOT NULL,
    start_time BIGINT NOT NULL,
    ocr_duration BIGINT,
    ocr_result LONGTEXT,
    error_message VARCHAR(1024),
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_ocr_process_log_1
    ON ocr_process_log(image_id ASC);

CREATE INDEX idx_ocr_process_log_2
    ON ocr_process_log(server_name);