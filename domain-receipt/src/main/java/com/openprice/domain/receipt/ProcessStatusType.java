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
    PARSED,
    VALIDATED
}
