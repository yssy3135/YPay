package com.ypay.payment.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.payment.application.port.in.FinishSettlementCommand;
import com.ypay.payment.application.port.in.FinishPaymentUseCase;
import com.ypay.payment.application.port.in.RequestNormalStatusPaymentUseCase;
import com.ypay.payment.application.port.in.RequestPaymentCommand;
import com.ypay.payment.application.port.in.RequestPaymentUseCase;
import com.ypay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {


    private final RequestPaymentUseCase requestPaymentUseCase;

    private final RequestNormalStatusPaymentUseCase requestNormalStatusPaymentUseCase;

    private final FinishPaymentUseCase finishPaymentUseCase;

    @PostMapping("/payment/request")
    void requestPayment(@RequestBody PaymentRequest request) {
            requestPaymentUseCase.requestPayment(
                    new RequestPaymentCommand(
                            request.getRequestMembershipId(),
                            request.getRequestPrice(),
                            request.getFranchiseId(),
                            request.getFranchiseFeeRate()
                    )
            );
    }


    @GetMapping(path = "/payment/normal-status")
    List<Payment> getNormalStatusPayments() {
        return requestNormalStatusPaymentUseCase.getNormalStatusPayments();
    }

    @PostMapping("/payment/finish-settlement")
    void finishPayment(FinishSettlementRequest finishSettlementRequest) {
        finishPaymentUseCase.finishPayment(
                new FinishSettlementCommand(
                        finishSettlementRequest.getPaymentId()
                )
        );
    }



}
