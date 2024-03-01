package com.ypay.membership.application.port.in;

import com.ypay.common.UseCase;
import com.ypay.membership.domain.Membership;

@UseCase
public interface ModifyMembershipUseCase {

    Membership modifyMembership(ModifyMembershipCommand command);
}
