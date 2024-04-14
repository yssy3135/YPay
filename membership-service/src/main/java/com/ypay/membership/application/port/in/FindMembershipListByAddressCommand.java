package com.ypay.membership.application.port.in;

import com.ypay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMembershipListByAddressCommand extends SelfValidating<FindMembershipListByAddressCommand> {

    private String addressName; // 관악구, 서초구, 강남구

}
