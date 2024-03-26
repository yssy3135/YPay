package com.ypay.money.application.port.out;

import com.ypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.ypay.money.domain.MemberMoney;

public interface GetMemberMoneyPort {

    MemberMoneyJpaEntity getMemberMoney(
            MemberMoney.MembershipId memberId
    );

}
