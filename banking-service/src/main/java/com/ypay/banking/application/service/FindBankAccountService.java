package com.ypay.banking.application.service;

import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.ypay.banking.application.port.in.FindBankAccountUseCase;
import com.ypay.banking.application.port.out.FindBankAccountPort;
import com.ypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBankAccountService implements FindBankAccountUseCase {

    private final FindBankAccountPort findBankAccountPort;

    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    @Override
    public RegisteredBankAccount findBankAccount(RegisteredBankAccount.MembershipId membershipId) {
        return registeredBankAccountMapper.mapToDomainEntity(findBankAccountPort.findBankAccount(membershipId));
    }
}
