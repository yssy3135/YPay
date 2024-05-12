package com.ypay.payment.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
    @Query("SELECT p " +
            "FROM PaymentJpaEntity p " +
            "WHERE p.paymentStatus = :status ")
    List<PaymentJpaEntity> findByPaymentStatus(int status);
}
