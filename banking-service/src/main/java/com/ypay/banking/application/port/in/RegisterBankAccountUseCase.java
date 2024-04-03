package com.ypay.banking.application.port.in;

import com.ypay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand registerBankAccountCommand);

    void registerBankAccountByEvent(RegisterBankAccountCommand command);
}
