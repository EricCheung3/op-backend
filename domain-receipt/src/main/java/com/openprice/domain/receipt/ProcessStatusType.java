package com.openprice.domain.receipt;

/**
 * State transition for receipt image processing.
 *
 */
public enum ProcessStatusType {
    CREATED,
    UPLOADED,
    QUEUED,
    STARTED,
    SCANNED,
    SCANNED_ERR,
    PARSED,
    VALIDATED
}
