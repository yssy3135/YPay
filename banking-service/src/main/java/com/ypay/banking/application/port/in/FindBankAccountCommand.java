package com.ypay.banking.application.port.in;

import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindBankAccountCommand extends SelfValidating<RegisteredBankAccount> {

    private String membershipId;

}
