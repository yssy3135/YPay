package com.ypay.membership.application.service;

import com.ypay.common.UseCase;
import com.ypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.ModifyMembershipCommand;
import com.ypay.membership.application.port.in.ModifyMembershipUseCase;
import com.ypay.membership.application.port.out.ModifyMembershipPort;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class ModifyMembershipService implements ModifyMembershipUseCase {

    private final ModifyMembershipPort modifyMembershipPort;

    private final MembershipMapper membershipMapper;

    @Override
    public Membership modifyMembership(ModifyMembershipCommand command) {

        MembershipJpaEntity jpaEntity = modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(command.getMembershipId()),
                new Membership.MembershipName(command.getName()),
                new Membership.MembershipEmail(command.getEmail()),
                new Membership.MembershipAddress(command.getAddress()),
                new Membership.MembershipIsValid(command.isValid()),
                new Membership.MembershipIsCorp(command.isCorp())
        );

        return membershipMapper.mapToDomainEntity(jpaEntity);
    }

}
