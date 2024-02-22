package com.ypay.membership.adapter.out.persistence;

import com.ypay.membership.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {



}
