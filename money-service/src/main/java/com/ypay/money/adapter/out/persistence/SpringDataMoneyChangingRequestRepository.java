package com.ypay.money.adapter.out.persistence;

import com.ypay.money.domain.MoneyChangingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMoneyChangingRequestRepository extends JpaRepository<MoneyChangingRequestJpaEntity, Long> {
}
