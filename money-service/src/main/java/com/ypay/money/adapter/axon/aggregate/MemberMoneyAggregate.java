package com.ypay.money.adapter.axon.aggregate;


import com.ypay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.ypay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.ypay.money.adapter.axon.command.RechargingMoneyRequestCreateCommand;
import com.ypay.money.adapter.axon.event.IncreaseMemberMoneyEvent;
import com.ypay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import com.ypay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import com.ypay.money.application.port.out.GetRegisteredBankAccountPort;
import com.ypay.money.application.port.out.RegisteredBankAccountAggregateIdentifier;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Aggregate;

import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate()
@Data
public class MemberMoneyAggregate {

    @AggregateIdentifier
    private String id;

    private Long membershipId;

    private int balance;


    public MemberMoneyAggregate() {
    }

    @CommandHandler
    public MemberMoneyAggregate(MemberMoneyCreatedCommand command) {
        System.out.println("MemberMoneyCreatedCommand Handler");

        apply(new MemberMoneyCreatedEvent(command.getMembershipId()));
    }

    @CommandHandler
    public String handle(@NotNull IncreaseMemberMoneyCommand command){
        System.out.println("IncreaseMemberMoneyCommand Handler");
        id = command.getAggregateIdentifier();

        // store event
        apply(new IncreaseMemberMoneyEvent(id, command.getMembershipId(), command.getAmount()));
        return id;
    }

    @CommandHandler
    public void handle(@NotNull RechargingMoneyRequestCreateCommand command, GetRegisteredBankAccountPort getRegisteredBankAccountPort){
        System.out.println("RechargingMoneyRequestCreateCommand Handler");
        id = command.getAggregateIdentifier();


        // new RechargingRequestCreatedEvent
        // banking 정보가 필요하다.-> Money service이기 때문에  bank와 관련없음 -> bank svc (get registeredBankAccount)를 위한 port 생성하고 사용해야함.
        RegisteredBankAccountAggregateIdentifier registeredBankAccountAggregateIdentifier = getRegisteredBankAccountPort.getRegisteredBankAccount(command.getMembershipId());

        // Saga Start
        apply(new RechargingRequestCreatedEvent(
           command.getRechargingRequestId(),
           command.getMembershipId(),
           command.getAmount(),
           registeredBankAccountAggregateIdentifier.getAggregateIdentifier(),
           registeredBankAccountAggregateIdentifier.getBankName(),
           registeredBankAccountAggregateIdentifier.getBankAccountNumber()
        ));

    }




    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

    @EventSourcingHandler
    public void on(IncreaseMemberMoneyEvent event) {
        System.out.println("IncreaseMemberMoneyEvent Sourcing Handler");
        id = event.getAggregateIdentifier();
        membershipId = Long.parseLong(event.getTargetMembershipId());
        balance = event.getAmount();
    }
}
