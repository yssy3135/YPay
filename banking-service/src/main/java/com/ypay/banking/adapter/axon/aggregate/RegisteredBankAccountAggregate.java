package com.ypay.banking.adapter.axon.aggregate;

import com.ypay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.ypay.banking.adapter.axon.event.CreateRegisteredBankAccountEvent;
import com.ypay.common.event.CheckRegisteredBankAccountCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate()
public class RegisteredBankAccountAggregate {


    @AggregateIdentifier
    private String id;

    private String rechargingRequestId;

    private String membershipId;

    private String bankName;

    private String bankAccountNumber;


    public RegisteredBankAccountAggregate() {
    }

    @CommandHandler
    public RegisteredBankAccountAggregate(CreateRegisteredBankAccountCommand command) {
        System.out.println("CreateRegisteredBankAccountCommand Sourcing Handler");
        apply(new CreateRegisteredBankAccountEvent(command.getMembershipId(), command.getBankName(), command.getBankAccountName()));

    }

    public void handle(@NotNull CheckRegisteredBankAccountCommand command) {
        System.out.println("CheckRegisteredBankAccountCommand Handler");

        // command를 통해 이 어그리거트(RegisteredBankAccount) 가 정상인지를 확인해야 한다.
        // port.get

//        command.get

    }


    @EventSourcingHandler
    public void on(CreateRegisteredBankAccountEvent event) {
        System.out.println("CreateRegisteredBankAccountEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = event.getMembershipId();
        bankName = event.getBankName();
        bankAccountNumber = event.getBankAccountNumber();
    }





}
