package com.ypay.membership.adapter.in.web;

import com.ypay.membership.application.port.in.FindMembershipCommand;
import com.ypay.membership.application.port.in.FindMembershipUseCase;
import com.ypay.membership.application.port.in.RegisterMembershipCommand;
import com.ypay.membership.application.port.in.RegisterMembershipUseCase;
import com.ypay.membership.domain.Membership;
import common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// WebAdapter
@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @PostMapping(path = "/membership/{membershipId}")
    Membership findMembershipByMembershipId(@PathVariable String membershipId) {

        // Usecase ~~ (reuqest)

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return findMembershipUseCase.findMembership(command);
    }

}
