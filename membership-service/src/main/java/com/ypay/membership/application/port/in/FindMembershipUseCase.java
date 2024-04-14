package com.ypay.membership.application.port.in;

import com.ypay.membership.domain.Membership;

import java.util.List;

// 어떻게 실제 비즈니스 로직과 동작이 될지 정의
public interface FindMembershipUseCase {

    Membership findMembership(FindMembershipCommand command);

    List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command);

}
