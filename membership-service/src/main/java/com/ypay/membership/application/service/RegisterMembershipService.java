package com.ypay.membership.application.service;

import com.ypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.RegisterMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import com.ypay.membership.application.port.out.RegisterMembershipPort;
import com.ypay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;


import javax.transaction.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class RegisterMembershipService implements RegisterMembershipUseCase {

    private final RegisterMembershipPort registerMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership registerMembership(RegisterMembershipCommand command) {

        // command -> entity (DB)


        // biz logic -> DB

        // external system
        // port adapter

        MembershipJpaEntity jpaEntity = registerMembershipPort.createMembership(
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        // membershipJpaEntity -> Membership
        return membershipMapper.mapToDomainEntity(jpaEntity);

    }
}
