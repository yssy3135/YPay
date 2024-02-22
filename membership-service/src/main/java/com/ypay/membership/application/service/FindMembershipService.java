package com.ypay.membership.application.service;

import com.ypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.FindMembershipCommand;
import com.ypay.membership.application.port.in.FindMembershipUseCase;
import com.ypay.membership.application.port.in.RegisterMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.application.port.out.RegisterMembershipPort;
import com.ypay.membership.domain.Membership;
import common.UseCase;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class FindMembershipService implements FindMembershipUseCase {

    private final FindMembershipPort findMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership findMembership(FindMembershipCommand command) {
        return membershipMapper.mapToDomainEntity(findMembershipPort.findMembership(new Membership.MembershipId(command.getMembershipId())));
    }
}
