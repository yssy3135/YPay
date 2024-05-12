package com.ypay.payment.application.port.out;

public interface ChangePaymentRequestStatusPort {

    void changePaymentRequestStatus(String paymentId, int status);
}
