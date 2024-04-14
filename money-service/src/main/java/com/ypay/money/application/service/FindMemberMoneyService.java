package com.ypay.money.application.service;

import com.ypay.common.UseCase;
import com.ypay.money.adapter.out.persistence.MemberMoneyMapper;
import com.ypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.ypay.money.application.port.in.FindMemberMoneyListByMembershipIdsCommand;
import com.ypay.money.application.port.in.FindMemberMoneyUseCase;
import com.ypay.money.application.port.out.GetMemberMoneyPort;
import com.ypay.money.domain.MemberMoney;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindMemberMoneyService implements FindMemberMoneyUseCase {

    private final GetMemberMoneyPort getMemberMoneyPort;

    private final MemberMoneyMapper mapper;

    @Override
    public List<MemberMoney> findMemberMoneyListByMembershipIds(FindMemberMoneyListByMembershipIdsCommand command) {
        // 여러개의 membership Ids를 기준으로, memberMoney 정보를 가져와야 한다.
        return getMemberMoneyPort.getMemberMoneyPort(command.getMembershipIds()).stream().map(mapper::mapToDomainEntity).collect(Collectors.toList());
    }
}
