package com.ypay.banking.adapter.out.external.bank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBankAccountRequest {

    private String bankName;

    private String bankAccountNumber;

    public GetBankAccountRequest(String bankName, String bankAccountNumber) {
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
    }
}
