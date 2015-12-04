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