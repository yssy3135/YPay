package com.ypay.banking.application.port.out;

import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.ypay.banking.domain.RegisteredBankAccount;
import org.axonframework.modelling.command.AggregateIdentifier;

public interface RegisterBankAccountPort {

    RegisteredBankAccountJpaEntity registerBankAccount(
            RegisteredBankAccount.MembershipId membershipId,
            RegisteredBankAccount.BankName bankName,
            RegisteredBankAccount.BankAccountNumber bankAccountNumber,
            RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
            RegisteredBankAccount.AggregateIdentifier  aggregateIdentifier
    );

}
