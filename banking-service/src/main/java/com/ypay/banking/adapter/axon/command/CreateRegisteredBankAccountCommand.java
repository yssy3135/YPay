package com.ypay.banking.adapter.axon.command;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class CreateRegisteredBankAccountCommand {

    private String membershipId;

    private String bankName;

    private String bankAccountName;
}
