package com.ypay.payment.adapter.in.web;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FinishSettlementRequest {

    private String paymentId;
}
