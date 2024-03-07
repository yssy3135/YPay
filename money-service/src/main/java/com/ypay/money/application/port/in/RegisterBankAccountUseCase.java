package com.ypay.money.application.port.in;


import com.ypay.money.domain.RegisteredBankAccount;

public interface RegisterBankAccountUseCase {

    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand registerBankAccountCommand);
}
