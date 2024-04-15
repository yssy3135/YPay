package com.ypay.money.aggregation.adapter.in.web;

import com.ypay.money.aggregation.application.port.in.GetMoneySumByAddressCommand;
import com.ypay.money.aggregation.application.port.in.GetMoneySumByAddressUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetMoneySumController {

    private final GetMoneySumByAddressUseCase getMoneySumByAddressUseCase;

    @PostMapping("money/aggregation/get-money-sum-by-address")
    int getMoneySumByAddress(@RequestBody GetMoneySumByAddressRequest request) {

        return getMoneySumByAddressUseCase.getMoneySumByAddress(
                GetMoneySumByAddressCommand.builder()
                        .address(request.getAddress())
                        .build()
        );

    }

}
