package com.ypay.banking.application.port.out;

import com.ypay.banking.adapter.out.external.bank.BankAccount;
import com.ypay.banking.adapter.out.external.bank.GetBankAccountRequest;

public interface RequestBankAccountInfoPort {

    BankAccount getBankAccountInfo(GetBankAccountRequest getBankAccountRequest);


}
