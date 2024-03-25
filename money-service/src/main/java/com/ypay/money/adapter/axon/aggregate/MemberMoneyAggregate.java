package com.ypay.money.adapter.axon.aggregate;


import com.ypay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.ypay.money.adapter.axon.event.MemberMoneyCreatedEvent;
import lombok.Data;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

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

    @EventSourcingHandler
    public void on(MemberMoneyCreatedEvent event) {
        System.out.println("MemberMoneyCreatedEvent Sourcing Handler");
        id = UUID.randomUUID().toString();
        membershipId = Long.parseLong(event.getMembershipId());
        balance = 0;
    }

}
