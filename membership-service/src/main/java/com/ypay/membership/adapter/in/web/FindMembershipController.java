package com.ypay.membership.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.membership.application.port.in.FindMembershipCommand;
import com.ypay.membership.application.port.in.FindMembershipListByAddressCommand;
import com.ypay.membership.application.port.in.FindMembershipUseCase;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindMembershipController {

    private final FindMembershipUseCase findMembershipUseCase;

    @GetMapping(path = "/membership/{membershipId}")
    Membership findMembershipByMembershipId(@PathVariable String membershipId) {

        FindMembershipCommand command = FindMembershipCommand.builder()
                .membershipId(membershipId)
                .build();

        return findMembershipUseCase.findMembership(command);
    }


    @GetMapping(path = "/membership/address/{addressName}")
    ResponseEntity<List<Membership>> findMembershipListByAddress(@PathVariable String addressName) {

        FindMembershipListByAddressCommand command = FindMembershipListByAddressCommand.builder()
                .addressName(addressName)
                .build();

        return ResponseEntity.ok(findMembershipUseCase.findMembershipListByAddress(command));
    }

}
