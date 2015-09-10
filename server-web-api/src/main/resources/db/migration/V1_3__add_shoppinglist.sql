CREATE TABLE store (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    name VARCHAR(255) NOT NULL,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE shopping_item (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    user_account_id VARCHAR(50) NOT NULL,
    store_id VARCHAR(50) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_price VARCHAR(50),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_shopping_item_1
    ON shopping_item(user_account_id, store_id);
    
ALTER TABLE shopping_item
    ADD CONSTRAINT fk_shopping_item_user_account
    FOREIGN KEY (user_account_id)
    REFERENCES user_account(id);

CREATE INDEX idx_shopping_item_2
    ON shopping_item(store_id ASC);
    
ALTER TABLE shopping_item
    ADD CONSTRAINT fk_shopping_item_store
    FOREIGN KEY (store_id)
    REFERENCES store(id);

