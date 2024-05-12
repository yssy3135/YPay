package com.ypay.payment.application.service;

import com.ypay.payment.application.port.in.*;
import com.ypay.payment.application.port.out.*;
import com.ypay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService implements RequestPaymentUseCase, RequestNormalStatusPaymentUseCase, FinishPaymentUseCase {


    private final CreatePaymentPort createPaymentPort;

    private final GetMembershipPort getMembershipPort;

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;

    private final GetNormalStatusPaymentPort getNormalStatusPaymentPort;

    private final ChangePaymentRequestStatusPort changePaymentRequestStatusPort;



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


    @Override
    public List<Payment> getNormalStatusPayments() {

        return getNormalStatusPaymentPort.getNormalStatusPayments();
    }

    @Override
    public void finishPayment(FinishSettlementCommand finishSettlementCommand) {
        changePaymentRequestStatusPort.changePaymentRequestStatus(finishSettlementCommand.getPaymentId(), 2);
    }


}
