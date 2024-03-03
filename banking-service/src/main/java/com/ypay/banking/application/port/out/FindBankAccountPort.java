package com.ypay.banking.application.port.out;

import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.ypay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountPort {

    RegisteredBankAccountJpaEntity findBankAccount(RegisteredBankAccount.MembershipId membershipId);
}
