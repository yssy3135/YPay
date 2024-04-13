package com.ypay.money.adapter.out.persistence;

import com.ypay.money.domain.MemberMoney;
import com.ypay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MemberMoneyMapper {
    public MemberMoney mapToDomainEntity(MemberMoneyJpaEntity memberMoneyJpaEntity) {
        return MemberMoney.generateMemberMoney(
               new MemberMoney.MemberMoneyId(memberMoneyJpaEntity.getMemberMoneyId().toString()),
                new MemberMoney.MembershipId(memberMoneyJpaEntity.getMembershipId().toString()),
                new MemberMoney.MoneyBalance(memberMoneyJpaEntity.getBalance())
        );
    }
}