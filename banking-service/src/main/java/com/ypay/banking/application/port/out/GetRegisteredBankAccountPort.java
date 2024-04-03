package com.ypay.banking.application.port.out;

import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.ypay.banking.application.port.in.GetRegisteredBankAccountCommand;

public interface GetRegisteredBankAccountPort {
    RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
