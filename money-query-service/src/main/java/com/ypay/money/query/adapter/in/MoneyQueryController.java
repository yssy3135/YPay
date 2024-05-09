package com.ypay.money.query.adapter.in;

import com.ypay.common.WebAdapter;
import com.ypay.money.query.application.port.in.QueryMoneySumByRegionQuery;
import com.ypay.money.query.application.port.in.QueryMoneySumByRegionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class MoneyQueryController {

    private final QueryMoneySumByRegionUseCase useCase;
    @GetMapping(path = "/money/query/get-money-sum-by-address/{address}")
    long getMoneySumByAddress(@PathVariable String address) {
        QueryMoneySumByRegionQuery query = QueryMoneySumByRegionQuery.builder()
                .address(address)
                .build();

//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        stopWatch.stop();
//        System.out.println("경과 시간(밀리초): " + stopWatch.getTotalTimeMillis());

        return useCase.queryMoneySumByRegion(query).getMoneySum();
    }
}
