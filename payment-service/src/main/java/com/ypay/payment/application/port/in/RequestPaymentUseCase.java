package com.ypay.payment.application.port.in;

import com.ypay.payment.domain.Payment;

import java.util.List;

public interface RequestPaymentUseCase {
    Payment requestPayment(RequestPaymentCommand command);


}
