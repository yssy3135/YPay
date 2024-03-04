package com.ypay.money.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, String> {

    @Query("SELECT rba " +
            "FROM RegisteredBankAccountJpaEntity rba " +
            "WHERE rba.membershipId = :membershipId")
    Optional<RegisteredBankAccountJpaEntity> findByMembershipId(String membershipId);
}
