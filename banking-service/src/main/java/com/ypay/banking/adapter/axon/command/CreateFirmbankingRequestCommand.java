package com.ypay.banking.adapter.axon.command;

import lombok.*;
import org.axonframework.commandhandling.CommandHandler;

@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateFirmbankingRequestCommand {

    private String fromBankName;
    private String fromBankAccountNumber;

    private String toBankName;
    private String toBankAccountNumber;

    private int moneyAmount;




}
