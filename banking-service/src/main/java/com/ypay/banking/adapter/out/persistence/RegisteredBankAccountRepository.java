package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.domain.RegisteredBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisteredBankAccountRepository extends JpaRepository<RegisteredBankAccountJpaEntity, String> {
}
