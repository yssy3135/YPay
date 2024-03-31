package com.ypay.banking.adapter.in.web;

import com.ypay.banking.application.port.in.RegisterBankAccountCommand;
import com.ypay.banking.application.port.in.RegisterBankAccountUseCase;
import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RegisterBankAccountController {

    private final RegisterBankAccountUseCase registerBankAccountUseCase;

    @PostMapping("/banking/account/register")
    RegisteredBankAccount registerBankAccount(@RequestBody RegisterBankAccountRequest registerBankAccountRequest) {

        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
                .membershipId(registerBankAccountRequest.getMembershipId())
                .bankName(registerBankAccountRequest.getBankName())
                .bankAccountNumber(registerBankAccountRequest.getBankAccountNumber())
                .isValid(registerBankAccountRequest.isValid())
                .build();

        RegisteredBankAccount registeredBankAccount = registerBankAccountUseCase.registerBankAccount(command);
        if(registeredBankAccount == null) {

            //TODO: Error handling
            return null;
        }

        return registeredBankAccount;
    }



}
