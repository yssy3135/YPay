package com.ypay.money.application.port.out;

import com.ypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.ypay.money.adapter.out.persistence.MoneyChangingRequestJpaEntity;
import com.ypay.money.domain.MemberMoney;
import com.ypay.money.domain.MoneyChangingRequest;

public interface IncreaseMoneyPort {

    MoneyChangingRequestJpaEntity createMoneyChangingRequest(
            MoneyChangingRequest.TargetMembershipId targetMembershipId,
            MoneyChangingRequest.MoneyChangingType moneyChangingType,
            MoneyChangingRequest.ChangingMoneyAmount changingMoneyAmount,
            MoneyChangingRequest.MoneyChangingStatus moneyChangingStatus,
            MoneyChangingRequest.Uuid uuid
    );

    MemberMoneyJpaEntity increaseMoney(
            MemberMoney.MembershipId memberId,
            int increaseMoneyAmount
    );
}
