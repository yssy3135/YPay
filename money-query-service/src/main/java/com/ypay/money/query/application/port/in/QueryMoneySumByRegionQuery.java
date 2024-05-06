package com.ypay.money.query.application.port.in;

import com.ypay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class QueryMoneySumByRegionQuery extends SelfValidating<QueryMoneySumByRegionQuery> {
    private final String address;
}
