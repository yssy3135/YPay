package com.ypay.money.application.port.in;

import com.ypay.common.SelfValidating;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class FindMemberMoneyListByMembershipIdsCommand extends SelfValidating<FindMemberMoneyListByMembershipIdsCommand> {

    @NotNull
    List<String> membershipIds;


    public FindMemberMoneyListByMembershipIdsCommand(List<String> membershipIds) {
        this.membershipIds = membershipIds;
        this.validateSelf();
    }
}
