CREATE TABLE shopping_store (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    user_account_id VARCHAR(50) NOT NULL,
    chain_code VARCHAR(255),
    display_name VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_shopping_store_1
    ON shopping_store(user_account_id);
    
CREATE INDEX idx_shopping_store_2
    ON shopping_store(chain_code);

ALTER TABLE shopping_store
    ADD CONSTRAINT fk_shopping_store_user_account
    FOREIGN KEY (user_account_id)
    REFERENCES user_account(id);


CREATE TABLE shopping_item (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    store_id VARCHAR(50) NOT NULL,
    catalog_code VARCHAR(255),
    name VARCHAR(255),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_shopping_item_1
    ON shopping_item(store_id);
    
ALTER TABLE shopping_item
    ADD CONSTRAINT fk_shopping_item_store
    FOREIGN KEY (store_id)
    REFERENCES shopping_store(id);

CREATE INDEX idx_shopping_item_2
    ON shopping_item(catalog_code);

