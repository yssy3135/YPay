package com.ypay.membership.application.port.in;

import com.ypay.membership.domain.JwtToken;

public interface AuthMembershipUseCase {

    JwtToken loginMembership(LoginMembershipCommand command);
//    JwtToken refreshJwtTokenByRefreshToken(RefreshTokenCommand command);
//    boolean validateJwtToken(ValidateTokenCommand command);
//    Membership getMembershipByJwtToken(ValidateTokenCommand command);
}
