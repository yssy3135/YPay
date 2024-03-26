package com.ypay.money.adapter.axon.command;

import com.ypay.common.SelfValidating;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class IncreaseMemberMoneyCommand extends SelfValidating<IncreaseMemberMoneyCommand> {

    @NotNull
    @TargetAggregateIdentifier
    private String aggregateIdentifier;
    @NotNull
    private final String membershipId;
    @NotNull
    private final int amount;

}