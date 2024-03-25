package com.ypay.money.application.port.in;

import com.ypay.money.adapter.in.web.CreateMemberMoneyRequest;

public interface CreateMemberMoneyUseCase {

    void createMemberMoney(CreateMemberMoneyCommand createMemberMoneyCommand);
}
