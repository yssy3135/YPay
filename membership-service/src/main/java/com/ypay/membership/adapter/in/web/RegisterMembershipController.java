package com.ypay.membership.adapter.in.web;

import com.ypay.membership.application.port.in.RegisterMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// WebAdapter
@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterMembershipController {

    private final RegisterMembershipUseCase registerMembershipUseCase;

    @PostMapping(path = "/membership/register")
    void registerMembership(@RequestBody RegisterMembershipRequest request) {


        // Usecase를 통해 request 처리


        // request -> Command

        RegisterMembershipCommand command = RegisterMembershipCommand.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(false)
                .isCorp(request.isCorp())
                .build();

        // Usecase ~~ (reuqest)
        registerMembershipUseCase.registerMembership(command);
    }
}
