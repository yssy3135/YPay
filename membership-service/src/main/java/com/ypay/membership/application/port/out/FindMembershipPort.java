package com.ypay.membership.application.port.out;

import com.ypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.ypay.membership.domain.Membership;

public interface FindMembershipPort {

    MembershipJpaEntity findMembership(
            Membership.MembershipId membershipId
    );
}
