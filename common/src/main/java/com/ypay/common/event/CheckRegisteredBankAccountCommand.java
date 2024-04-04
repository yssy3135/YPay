package com.ypay.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckRegisteredBankAccountCommand {


    @TargetAggregateIdentifier
    private String aggregateIdentifier; // RegisteredBankAccount

    private String rechargeRequestId;

    private String membershipId;

    private String checkRegisterBankAccountId;

    private String bankName;

    private String bankAccountNumber;

    private int amount;




}
