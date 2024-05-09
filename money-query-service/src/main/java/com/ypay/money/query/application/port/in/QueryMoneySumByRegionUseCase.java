package com.ypay.money.query.application.port.in;

import com.ypay.money.query.domain.MoneySumByRegion;

public interface QueryMoneySumByRegionUseCase {
    MoneySumByRegion queryMoneySumByRegion (QueryMoneySumByRegionQuery query);
}
