package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.ypay.banking.application.port.out.FindBankAccountPort;
import com.ypay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.ypay.banking.application.port.out.RegisterBankAccountPort;
import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindBankAccountPort, GetRegisteredBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

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
        List<RegisteredBankAccountJpaEntity> entityList = bankAccountRepository.findByMembershipId(membershipId.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }

    @Override
    public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entityList = bankAccountRepository.findByMembershipId(command.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }
}
