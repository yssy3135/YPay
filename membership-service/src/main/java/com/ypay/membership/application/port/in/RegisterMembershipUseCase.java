package com.ypay.membership.application.port.in;

import com.ypay.membership.domain.Membership;

// 어떻게 실제 비즈니스 로직과 동작이 될지 정의
public interface RegisterMembershipUseCase {

    Membership registerMembership(RegisterMembershipCommand command);

}
