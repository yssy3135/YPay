package com.ypay.payment.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.payment.application.port.in.RequestPaymentCommand;
import com.ypay.payment.application.port.in.RequestPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestPaymentController {


    private final RequestPaymentUseCase requestPaymentUseCase;

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
}
