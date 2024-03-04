package com.ypay.money.application.port.in;

import com.ypay.banking.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand registerBankAccountCommand);
}
