CREATE TABLE user_reset_password_request (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,

    email VARCHAR(100) NOT NULL,
    request_time TIMESTAMP NOT NULL,
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_user_reset_password_request_1
    ON user_reset_password_request(email);
