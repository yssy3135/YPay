package com.ypay.banking.adapter.in.web;

import com.ypay.banking.application.port.in.FindBankAccountUseCase;
import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindBankAccountController {

    private final FindBankAccountUseCase findBankAccountUseCase;


    @GetMapping("/banking/account/membership/{membershipId}")
    public RegisteredBankAccount findBankAccountByMembershipId(@PathVariable String membershipId) {
        return findBankAccountUseCase.findBankAccount(new RegisteredBankAccount.MembershipId(membershipId));
    }

}
