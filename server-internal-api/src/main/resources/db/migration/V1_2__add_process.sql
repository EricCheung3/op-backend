CREATE TABLE process_log (
    id VARCHAR(50),
    version BIGINT NOT NULL DEFAULT 1,

    image_id VARCHAR(50) NOT NULL,
    username VARCHAR(100) NOT NULL,
    server_name VARCHAR(255) NOT NULL,
    start_time BIGINT NOT NULL,
    ocr_duration BIGINT,
    ocr_result LONGTEXT,
    parser_duration BIGINT,
    parser_result LONGTEXT,
    error_message VARCHAR(1024),
    
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE INDEX idx_process_log_1
    ON process_log(image_id ASC);

CREATE INDEX idx_process_log_2
    ON process_log(server_name);