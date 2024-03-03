package com.ypay.banking.application.service;

import com.ypay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.ypay.banking.adapter.out.external.bank.FirmbankingResult;
import com.ypay.banking.adapter.out.persistence.FirmbankingRequestJpaEntity;
import com.ypay.banking.adapter.out.persistence.FirmbankingRequestMapper;
import com.ypay.banking.application.port.in.RequestFirmbankingCommand;
import com.ypay.banking.application.port.in.RequestFirmbankingUseCase;
import com.ypay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.ypay.banking.application.port.out.RequestFirmbankingPort;
import com.ypay.banking.domain.FirmbankingRequest;
import com.ypay.common.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class FirmbankingService implements RequestFirmbankingUseCase {

    private final FirmbankingRequestMapper mapper;
    private final RequestFirmbankingPort requestFirmbankingPort;
    private final RequestExternalFirmbankingPort requestExternalFirmbankingPort;



    @Override
    public FirmbankingRequest requestFirmbanking(RequestFirmbankingCommand command) {

        // Business Logic

        // a -> b 계좌

        // 1. 요청에 대해 정보를 먼저 write, "요청" 상태로
        FirmbankingRequestJpaEntity requestedEntity = requestFirmbankingPort.createFirmbankingRequest(
                new FirmbankingRequest.FromBankName(command.getFromBankName()),
                new FirmbankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmbankingRequest.ToBankName(command.getToBankName()),
                new FirmbankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmbankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmbankingRequest.FirmbankingStatus(0)
        );


        // 2. 외부 은행에 펌뱅킹 요청
        FirmbankingResult result = requestExternalFirmbankingPort.requestExternalFirmbanking(new ExternalFirmbankingRequest(
                command.getFromBankName(),
                command.getFromBankAccountNumber(),
                command.getToBankName(),
                command.getToBankAccountNumber()
        ));

        // Transactional UUID
        UUID randomUUID = UUID.randomUUID();
        requestedEntity.setUuid(randomUUID.toString());

        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 update
        if( result.getResultCode() == 0 ) {
            // 성공
            requestedEntity.setFirmbankingStatus(1);
        } else {
            // 실패
            requestedEntity.setFirmbankingStatus(2);
        }


        // 4. 결과를 return
        return mapper.mapToDomainEntity(requestFirmbankingPort.modifyFirmbankingRequest(requestedEntity), randomUUID);

    }
}
