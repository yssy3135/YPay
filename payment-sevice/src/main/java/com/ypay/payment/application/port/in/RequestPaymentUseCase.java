package com.ypay.payment.application.port.in;

import com.ypay.payment.domain.Payment;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);
}
