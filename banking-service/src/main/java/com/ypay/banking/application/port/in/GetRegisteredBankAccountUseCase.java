package com.ypay.banking.application.port.in;

import com.ypay.banking.domain.RegisteredBankAccount;

public interface GetRegisteredBankAccountUseCase {
    RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command);
}
