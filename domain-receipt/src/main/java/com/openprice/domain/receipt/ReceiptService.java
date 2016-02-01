package com.openprice.domain.receipt;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

@Service
public class ReceiptService {
    private final ReceiptRepository receiptRepository;
    private final ReceiptFeedbackRepository receiptFeedbackRepository;

    @Inject
    public ReceiptService(final ReceiptRepository receiptRepository,
                          final ReceiptFeedbackRepository receiptFeedbackRepository) {
        this.receiptRepository = receiptRepository;
        this.receiptFeedbackRepository = receiptFeedbackRepository;
    }

    /**
     * Add feedback from user for receipt, and set receipt <code>needFeedback</code> to false.
     * @param receipt
     * @param rating
     * @param comment
     * @return
     */
    public ReceiptFeedback addFeedback(final Receipt receipt, final Integer rating, final String comment) {
        ReceiptFeedback feedback = new ReceiptFeedback();
        feedback.setReceipt(receipt);
        feedback.setRating(rating);
        feedback.setComment(comment);

        feedback = receiptFeedbackRepository.save(feedback);

        receipt.setNeedFeedback(false);
        receiptRepository.save(receipt);

        return feedback;
    }

}
