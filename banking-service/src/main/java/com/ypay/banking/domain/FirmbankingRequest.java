package com.ypay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FirmbankingRequest {

    @Getter private final String firmbankingRequestId;
    @Getter private final String fromBankName;
    @Getter private final String fromBankAccountNumber;
    @Getter private final String toBankName;
    @Getter private final String toBankAccountNumber;

    // todo: 추후 bigdecimal로 수정
    @Getter private final int moneyAmount;
    // 0: 요청, 1: 완료, 2: 실패
    @Getter private final int firmbankingStatus;
    @Getter private final UUID uuid;
    @Getter private final String aggregateIdentifier;

    public static FirmbankingRequest generateFirmbankingRequest (
            FirmbankingRequestId firmbankingRequestId,
            FromBankName fromBankName,
            FromBankAccountNumber fromBankAccountNumber,
            ToBankName toBankName,
            ToBankAccountNumber toBankAccountNumber,
            MoneyAmount moneyAmount,
            FirmbankingStatus firmbankingStatus,
            UUID uuid,
            FirmbankingAggregateIdentifier firmbankingAggregateIdentifier
    ){
        return new FirmbankingRequest(
                firmbankingRequestId.getFirmbankingRequestId(),
                fromBankName.getFromBankName(),
                fromBankAccountNumber.getFromBankAccountNumber(),
                toBankName.getToBankName(),
                toBankAccountNumber.getToBankAccountNumber(),
                moneyAmount.getMoneyAmount(),
                firmbankingStatus.getFirmbankingStatus(),
                uuid,
                firmbankingAggregateIdentifier.getAggregateIdentifier()
        );
    }


    @Value
    public static class FirmbankingRequestId {
        public FirmbankingRequestId(String value) {
            this.firmbankingRequestId = value;
        }

        private String firmbankingRequestId;
    }
    @Value
    public static class FromBankName {
        public FromBankName(String value) {
            this.fromBankName = value;
        }

        private String fromBankName;
    }

    @Value
    public static class FromBankAccountNumber {
        public FromBankAccountNumber(String value) {
            this.fromBankAccountNumber = value;
        }

        private String fromBankAccountNumber;
    }
    @Value
    public static class ToBankName {
        public ToBankName(String value) {
            this.toBankName = value;
        }

        private String toBankName;
    }

    @Value
    public static class ToBankAccountNumber {
        public ToBankAccountNumber(String value) {
            this.toBankAccountNumber = value;
        }

        private String toBankAccountNumber;
    }

    @Value
    public static class MoneyAmount {
        public MoneyAmount(int value) {
            this.moneyAmount = value;
        }

        private int moneyAmount;
    }

    @Value
    public static class FirmbankingStatus {
        public FirmbankingStatus(int value) {
            this.firmbankingStatus = value;
        }

        private int firmbankingStatus;
    }

    @Value
    public static class FirmbankingAggregateIdentifier {
        public FirmbankingAggregateIdentifier(String value) {
            this.aggregateIdentifier = value;
        }
        private String aggregateIdentifier;
    }
}
