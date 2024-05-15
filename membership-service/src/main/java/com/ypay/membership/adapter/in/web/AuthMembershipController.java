package com.ypay.membership.adapter.in.web;

import com.ypay.membership.application.port.in.LoginMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import com.ypay.membership.domain.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ypay.common.WebAdapter;

// com.ypay.common.WebAdapter
@WebAdapter
@RestController
@RequiredArgsConstructor
public class AuthMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/login")
    JwtToken registerMembership(@RequestBody LoginMembershipRequest request) {

        LoginMembershipCommand command = LoginMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .build();

        return
    }

}
