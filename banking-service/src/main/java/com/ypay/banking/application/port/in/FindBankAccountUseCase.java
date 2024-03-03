package com.ypay.banking.application.port.in;

import com.ypay.banking.domain.RegisteredBankAccount;

public interface FindBankAccountUseCase {


    RegisteredBankAccount findBankAccount(RegisteredBankAccount.MembershipId membershipId);
}
