package com.ypay.membership.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.membership.application.port.in.RegisterMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// com.ypay.common.WebAdapter
@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    Membership registerMembership(@RequestBody RegisterMembershipRequest request) {

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isCorp(request.isCorp())
                .build();

        // Usecase ~~ (reuqest)
        return registerMembershipUseCase.registerMembership(command);
    }

}
