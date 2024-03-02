package com.ypay.banking.adapter.out.external.bank;

import com.ypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.ypay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {




    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {


        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
