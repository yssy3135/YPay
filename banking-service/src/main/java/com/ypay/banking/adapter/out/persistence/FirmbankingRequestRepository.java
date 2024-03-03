package com.ypay.banking.adapter.out.persistence;

import com.ypay.banking.adapter.in.web.RequestFirmbankingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirmbankingRequestRepository extends JpaRepository<FirmbankingRequestJpaEntity, Long> {
}
