package com.ypay.money.application.port.out;

import com.ypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.ypay.money.domain.MemberMoney;

import java.util.List;

public interface GetMemberMoneyListPort {

    List<MemberMoneyJpaEntity> getMemberMoneyPort(List<String> membershipIds);
}
