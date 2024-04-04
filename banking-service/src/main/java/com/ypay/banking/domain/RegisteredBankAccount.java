package com.ypay.banking.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisteredBankAccount {

    @Getter private final String registeredBankAccountId;

    @Getter private final String membershipId;

    @Getter private final String bankName;

    @Getter private final String bankAccountNumber;

    @Getter private final boolean linkedStatusIsValid;

    @Getter private final String aggregateIdentifier;


    // private final을 사용하는 이유는
    // AccessLevel.PRIVATE을 통해 객체 내부에서만 접근할 수 있도록 지정
    // 오염이 되면 안되는 도메인 클래스
    // 고객 정보, 핵심 도메인


    public static RegisteredBankAccount generateRegisteredBankAccount(
          RegisteredBankAccountId registeredBankAccountId,
          MembershipId membershipId,
          BankName bankName,
          BankAccountNumber bankAccountNumber,
          LinkedStatusIsValid linkedStatusIsValid,
          AggregateIdentifier aggregateIdentifier
    ) {
        return new RegisteredBankAccount(
                registeredBankAccountId.registeredBankAccountId,
                membershipId.membershipId,
                bankName.bankName,
                bankAccountNumber.bankAccountNumber,
                linkedStatusIsValid.linkedStatusIsValid,
                aggregateIdentifier.aggregateIdentifier
        );
    }


    @Value
    public static class RegisteredBankAccountId {
        public RegisteredBankAccountId(String value) {
            this.registeredBankAccountId = value;
        }
        String registeredBankAccountId ;
    }

    @Value
    public static class MembershipId {
        public MembershipId(String value) {
            this.membershipId = value;
        }
        String membershipId ;
    }

    @Value
    public static class BankName {
        public BankName(String value) {
            this.bankName = value;
        }
        String bankName ;
    }

    @Value
    public static class BankAccountNumber {
        public BankAccountNumber(String value) {
            this.bankAccountNumber = value;
        }
        String bankAccountNumber ;
    }

    @Value
    public static class LinkedStatusIsValid {
        public LinkedStatusIsValid(boolean value) {
            this.linkedStatusIsValid = value;
        }
        boolean linkedStatusIsValid ;
    }

    @Value
    public static class AggregateIdentifier {
        public AggregateIdentifier(String value) {
            this.aggregateIdentifier = value;
        }
        String aggregateIdentifier ;
    }
}
