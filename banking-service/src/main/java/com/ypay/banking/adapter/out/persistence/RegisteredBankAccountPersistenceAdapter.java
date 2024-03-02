package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.application.port.out.FindBankAccountPort;
import com.ypay.banking.application.port.out.RegisterBankAccountPort;
import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort {

    private final RegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity registerBankAccount(RegisteredBankAccount.MembershipId membershipId, RegisteredBankAccount.BankName bankName, RegisteredBankAccount.BankAccountNumber bankAccountNumber, RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid) {

        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }

    @Override
    public RegisteredBankAccountJpaEntity findBankAccount(RegisteredBankAccount.MembershipId membershipId) {

        return bankAccountRepository.findByMembershipId(membershipId.getMembershipId()).orElseThrow(() -> new RuntimeException("not found"));
    }
}
