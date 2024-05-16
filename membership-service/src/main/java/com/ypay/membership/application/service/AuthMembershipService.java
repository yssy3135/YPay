package com.ypay.membership.application.service;

import com.ypay.common.UseCase;
import com.ypay.membership.adapter.out.persistence.MembershipJpaEntity;
import com.ypay.membership.adapter.out.persistence.MembershipMapper;
import com.ypay.membership.application.port.in.AuthMembershipUseCase;
import com.ypay.membership.application.port.in.LoginMembershipCommand;
import com.ypay.membership.application.port.in.RefreshTokenCommand;
import com.ypay.membership.application.port.in.ValidateTokenCommand;
import com.ypay.membership.application.port.out.AuthMembershipPort;
import com.ypay.membership.application.port.out.FindMembershipPort;
import com.ypay.membership.application.port.out.ModifyMembershipPort;
import com.ypay.membership.domain.JwtToken;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional
public class AuthMembershipService implements AuthMembershipUseCase {

    private final AuthMembershipPort authMembershipPort;
    private final FindMembershipPort findMembershipPort;
    private final ModifyMembershipPort modifyMembershipPort;
    private final MembershipMapper mapper;

    @Override
    public JwtToken loginMembership(LoginMembershipCommand command) {

        String membershipId = command.getMembershipId();
        Membership membership = mapper.mapToDomainEntity(findMembershipPort.findMembership(
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

    @Override
    public JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command) {

        String requestedRefreshToken = command.getRefreshToken();
        boolean isValid = authMembershipPort.validateJwtToken(requestedRefreshToken);

        if(isValid) {
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(requestedRefreshToken);

            String membershipIdString = membershipId.getMembershipId();

            MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);

            if(!membershipJpaEntity.getRefreshToken().equals(
                    command.getRefreshToken()
            )) {
                return null;
            }

            // 고객의 refresh token 정보와, 요청받은 refresh token 정보가 일치하는지 확인 된 상태

            if(membershipJpaEntity.isValid()) {
                String newJwtToken = authMembershipPort.generateRefreshToken(new Membership.MembershipId(membershipIdString));

                return JwtToken.generateJwtToken(
                        new JwtToken.MembershipId(membershipIdString),
                        new JwtToken.MembershipJwtToken(newJwtToken),
                        new JwtToken.MembershipRefreshToken(requestedRefreshToken)
                );
            }

        }


        return null;
    }

    @Override
    public boolean validateJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        return authMembershipPort.validateJwtToken(jwtToken);
    }

    @Override
    public Membership getMembershipByJwtToken(ValidateTokenCommand command) {
        String jwtToken = command.getJwtToken();
        boolean isValid = authMembershipPort.validateJwtToken(jwtToken);

        if(isValid) {
            Membership.MembershipId membershipId = authMembershipPort.parseMembershipIdFromToken(jwtToken);
            String membershipIdString = membershipId.getMembershipId();

            MembershipJpaEntity membershipJpaEntity = findMembershipPort.findMembership(membershipId);
            if (!membershipJpaEntity.getRefreshToken().equals(command.getJwtToken())) {
                return null;
            }

            return mapper.mapToDomainEntity(membershipJpaEntity);
        }

        return null;
    }
}
