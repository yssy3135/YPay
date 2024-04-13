package com.ypay.money.application.port.in;

import com.ypay.money.adapter.in.web.FindMemberMoneyListByMembershipIdsRequest;
import com.ypay.money.domain.MemberMoney;

import java.util.List;

public interface FindMemberMoneyUseCase {

    List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command);
}
