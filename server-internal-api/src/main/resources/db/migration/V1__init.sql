CREATE TABLE user_profile (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    first_name VARCHAR(100),
    middle_name VARCHAR(100),
    last_name VARCHAR(100),
    phone VARCHAR(50),
    address1 VARCHAR(255),
    address2 VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(20),
    zip VARCHAR(20),
    country VARCHAR(100),

    PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE user_account (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    account_locked BOOLEAN DEFAULT false,
    trusted_account BOOLEAN DEFAULT false,
    activated BOOLEAN DEFAULT false,
    profile_id VARCHAR(50) NOT NULL,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_user_account_1
    ON user_account(email);
ALTER TABLE user_account
    ADD CONSTRAINT fk_user_account_user_profile
    FOREIGN KEY (profile_id)
    REFERENCES user_profile(id)
    ON DELETE CASCADE;

CREATE TABLE user_role (
    user_account_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE INDEX idx_user_role_1
    ON user_role(user_account_id ASC);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_user_account
    FOREIGN KEY (user_account_id)
    REFERENCES user_account(id)
    ON DELETE CASCADE;

CREATE TABLE admin_account (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,
    created_by VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    last_modified_time TIMESTAMP,

    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    title VARCHAR(100),
    account_locked BOOLEAN DEFAULT false,
    activated BOOLEAN DEFAULT false,

    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_admin_account_1
    ON admin_account(username);

CREATE TABLE admin_role (
    admin_account_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE INDEX idx_admin_role_1
    ON admin_role(admin_account_id ASC);

ALTER TABLE admin_role
    ADD CONSTRAINT fk_admin_role_admin_account
    FOREIGN KEY (admin_account_id)
    REFERENCES admin_account(id)
    ON DELETE CASCADE;


CREATE TABLE user_reset_password_request (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,

    email VARCHAR(100) NOT NULL,
    request_time TIMESTAMP NOT NULL,
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_user_reset_password_request_1
    ON user_reset_password_request(email);
