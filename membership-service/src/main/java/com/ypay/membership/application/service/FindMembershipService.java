package com.ypay.membership.application.service;

import com.ypay.common.UseCase;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.FindMembershipCommand;
import com.ypay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.ypay.membership.application.port.in.FindMembershipUseCase;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public List<Membership> findMembershipListByAddress(FindMembershipListByAddressCommand command) {

        return findMembershipPort.findMembershipListByAddress(new Membership.MembershipAddress(command.getAddressName())).stream()
                .map(membershipMapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }
}
