package com.ypay.membership.application.service;

import com.ypay.common.UseCase;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.AuthMembershipUseCase;
import com.ypay.membership.application.port.in.LoginMembershipCommand;
import com.ypay.membership.application.port.out.AuthMembershipPort;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.application.port.out.ModifyMembershipPort;
import com.ypay.membership.domain.JwtToken;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMembershipService implements AuthMembershipUseCase {

    private final AuthMembershipPort authMembershipPort;
    private final FindMembershipPort findMembershipPort;
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper membershipMapper;

    @Override
    public JwtToken loginMembership(LoginMembershipCommand command) {

        String membershipId = command.getMembershipId();
        Membership membership = membershipMapper.mapToDomainEntity(findMembershipPort.findMembership(
                new Membership.MembershipId(membershipId)
        ));

        String jwtToken = authMembershipPort.generateJwtToken(
                new Membership.MembershipId(membershipId)
        );

        String refreshToken = authMembershipPort.generateRefreshToken(
                new Membership.MembershipId(membershipId)
        );

        modifyMembershipPort.modifyMembership(
                new Membership.MembershipId(membershipId),
                new Membership.MembershipName(membership.getName()),
                new Membership.MembershipEmail(membership.getEmail()),
                new Membership.MembershipAddress(membership.getAddress()),
                new Membership.MembershipIsValid(membership.isValid()),
                new Membership.MembershipIsCorp(membership.isCrop()),
                new Membership.MembershipRefreshToken(refreshToken)
        );


        return JwtToken.generateJwtToken(
                new JwtToken.MembershipId(membershipId),
                new JwtToken.MembershipJwtToken(jwtToken),
                new JwtToken.MembershipRefreshToken(refreshToken)
        );
    }
}
