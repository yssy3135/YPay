package com.ypay.money.application.port.out;

import com.ypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.ypay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.ypay.money.adapter.out.service.Membership;
import com.ypay.money.domain.MemberMoney;
import com.ypay.money.domain.MoneyChangingRequest;

import java.lang.reflect.Member;

public interface CreateMemberMoneyPort {

    void createMemberMoney(
            MemberMoney.MembershipId membershipId,
            MemberMoney.MoneyAggregateIdentifier moneyAggregateIdentifier
    );

}
