package com.ypay.membership.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.membership.application.port.in.ModifyMembershipCommand;
import com.ypay.membership.application.port.in.ModifyMembershipUseCase;
import com.ypay.membership.domain.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping("/membership/modify")
    ResponseEntity<Membership> modifyMembershipById(@RequestBody ModifyMembershipRequest request) {

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .isCorp(request.isCorp())
                .isValid(request.isValid())
                .build();



        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }


}
