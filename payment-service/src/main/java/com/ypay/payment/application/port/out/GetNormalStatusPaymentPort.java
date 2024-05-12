package com.ypay.payment.application.port.out;

import com.ypay.payment.domain.Payment;

import java.util.List;

public interface GetNormalStatusPaymentPort {

    List<Payment> getNormalStatusPayments();

}
