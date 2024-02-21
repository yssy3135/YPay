package com.ypay.membership.application.port.in;

import common.UseCase;

// 어떻게 실제 비즈니스 로직과 동작이 될지 정의
@UseCase
public interface RegisterMembershipUseCase {

    void registerMembership(RegisterMembershipCommand command);

}
