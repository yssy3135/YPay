package com.ypay.payment.application.service;

import com.ypay.payment.application.port.in.RequestPaymentCommand;
import com.ypay.payment.application.port.in.RequestPaymentUseCase;
import com.ypay.payment.application.port.out.CreatePaymentPort;
import com.ypay.payment.application.port.out.GetMembershipPort;
import com.ypay.payment.application.port.out.GetRegisteredBankAccountPort;
import com.ypay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService implements RequestPaymentUseCase {


    private final CreatePaymentPort createPaymentPort;

    private final GetMembershipPort getMembershipPort;

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    // TODO Money Service ->

    @Override
    public Payment requestPayment(RequestPaymentCommand command) {

        //TODO 충전 멤버십, 머니 유효성 확인 해줘야함
        getMembershipPort.getMembership(command.getRequestMembershipId());

        getRegisteredBankAccountPort.getRegisteredBankAccount(command.getRequestMembershipId());


        return createPaymentPort.createPayment(
                command.getRequestMembershipId(),
                command.getRequestPrice(),
                command.getFranchiseId(),
                command.getFranchiseFeeRate()
        );
    }
}
