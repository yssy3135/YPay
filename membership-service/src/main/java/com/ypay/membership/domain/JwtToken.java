package com.ypay.membership.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {

    @Getter private final String membershipId;

    @Getter private final String accessToken;

    @Getter private final String refreshToken;


    public static JwtToken generateJwtToken(
            MembershipId membershipId,
            MembershipJwtToken membershipJwtToken,
            MembershipRefreshToken membershipRefreshToken
    ) {
        return new JwtToken(
                membershipId.membershipId,
                membershipJwtToken.membershipJwtToken,
                membershipRefreshToken.membershipRefreshToken
        );
    }


    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }

        String membershipId;
    }


    @Value
    public static class MembershipJwtToken {
        public MembershipJwtToken(String value) {
            this.membershipJwtToken = value;
        }

        String membershipJwtToken;
    }

    @Value
    public static class MembershipRefreshToken {
        public MembershipRefreshToken(String value) {
            this.membershipRefreshToken = value;
        }

        String membershipRefreshToken;
    }



}
