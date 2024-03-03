package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.application.port.out.RequestFirmbankingPort;
import com.ypay.banking.domain.FirmbankingRequest;
import com.ypay.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;


@PersistenceAdapter
@RequiredArgsConstructor
public class FirmbankingRequestPersistenceAdapter implements RequestFirmbankingPort {

    private final FirmbankingRequestRepository firmbankingRequestRepository;


    @Override
    public FirmbankingRequestJpaEntity createFirmbankingRequest(FirmbankingRequest.FromBankName fromBankName, FirmbankingRequest.FromBankAccountNumber fromBankAccountNumber, FirmbankingRequest.ToBankName toBankName, FirmbankingRequest.ToBankAccountNumber toBankAccountNumber, FirmbankingRequest.MoneyAmount moneyAmount, FirmbankingRequest.FirmbankingStatus firmbankingStatus) {
        FirmbankingRequestJpaEntity firmbankingRequestJpaEntity = new FirmbankingRequestJpaEntity(
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                UUID.randomUUID()
        );


        return firmbankingRequestRepository.save(firmbankingRequestJpaEntity);
    }

    @Override
    public FirmbankingRequestJpaEntity modifyFirmbankingRequest(FirmbankingRequestJpaEntity entity) {
        return firmbankingRequestRepository.save(entity);
    }
}
