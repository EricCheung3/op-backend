ALTER TABLE receipt
    ADD status VARCHAR(50);
UPDATE receipt
    SET status = 'HAS_RESULT';
ALTER TABLE receipt
    MODIFY status VARCHAR(50);

ALTER TABLE receipt
    ADD receipt_date DATE;
UPDATE receipt
    SET receipt_date = '2016-01-01';
ALTER TABLE receipt
    MODIFY receipt_date DATE NOT NULL;

