package com.ypay.membership.application.port.in;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginMembershipCommand {
    String membershipId;
}
