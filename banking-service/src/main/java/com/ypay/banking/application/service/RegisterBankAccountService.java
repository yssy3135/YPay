package com.ypay.banking.application.service;

import com.ypay.banking.adapter.out.external.bank.BankAccount;
import com.ypay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.ypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.ypay.banking.application.port.in.RegisterBankAccountCommand;
import com.ypay.banking.application.port.in.RegisterBankAccountUseCase;
import com.ypay.banking.application.port.out.RegisterBankAccountPort;
import com.ypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.ypay.banking.domain.RegisteredBankAccount;
import com.ypay.common.UseCase;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class RegisterBankAccountService implements RegisterBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;

    private final RegisteredBankAccountMapper mapper;

    private final RequestBankAccountInfoPort requestBankAccountInfoPort;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {

        // 은행 계좌를 등록하는 서비스 (비즈니스)

        // 멤버 서비스도 확인? 여기서는 skip

        // 1. 외부 실제 은행에 등록이 가능한 계좌인지(정상인지) 확인
        // 외부의 은행에 이 계좌 정상인지 확인 필요
        // Biz Login -> Adapter -> External System

        // 2. 등록 가능한 계좌라면, 등록. 성광시, 등록에 성공한 등록 정보를 리턴
        // 2-1. 등록 가능하지 않은 계좌라면 에러르 리턴

        // 실제 외부의 은행계좌 정보를 Get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));

        if(accountInfo.isValid()) {
            RegisteredBankAccountJpaEntity registeredBankAccountJpaEntity = registerBankAccountPort.registerBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isValid())
            );

            return mapper.mapToDomainEntity(registeredBankAccountJpaEntity);
        } else {
            return null;
        }
    }
}
