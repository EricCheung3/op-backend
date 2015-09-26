CREATE TABLE store_chain (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    code VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    categories VARCHAR(255),
    identify_fields VARCHAR(4095),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_store_chain_1
    ON store_chain(code ASC);

CREATE TABLE store_branch (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    chain_id VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    gst_number VARCHAR(20),
    phone VARCHAR(50),
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(20),
    zip VARCHAR(20),
    country VARCHAR(100),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_store_branch_1
    ON store_branch(chain_id ASC);
ALTER TABLE store_branch
    ADD CONSTRAINT fk_store_branch_store_chain
    FOREIGN KEY (chain_id)
    REFERENCES store_chain(id);


CREATE TABLE shopping_item (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    user_account_id VARCHAR(50) NOT NULL,
    chain_id VARCHAR(50) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    item_price VARCHAR(50),

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_shopping_item_1
    ON shopping_item(user_account_id, chain_id);
    
ALTER TABLE shopping_item
    ADD CONSTRAINT fk_shopping_item_user_account
    FOREIGN KEY (user_account_id)
    REFERENCES user_account(id);

CREATE INDEX idx_shopping_item_2
    ON shopping_item(chain_id ASC);
    
ALTER TABLE shopping_item
    ADD CONSTRAINT fk_shopping_item_store
    FOREIGN KEY (chain_id)
    REFERENCES store_chain(id);

