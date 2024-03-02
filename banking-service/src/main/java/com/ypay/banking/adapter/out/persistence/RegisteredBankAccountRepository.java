package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.domain.RegisteredBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, String> {

    @Query("SELECT rba " +
            "FROM RegisteredBankAccountJpaEntity rba " +
            "WHERE rba.membershipId = :membershipId")
    Optional<RegisteredBankAccountJpaEntity> findByMembershipId(String membershipId);
}
