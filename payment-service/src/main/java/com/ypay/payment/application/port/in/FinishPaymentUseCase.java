package com.ypay.payment.application.port.in;

public interface FinishPaymentUseCase {

    void finishPayment(FinishSettlementCommand finishSettlementCommand);
}
