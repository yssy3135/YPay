package com.ypay.membership.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataMembershipRepository extends JpaRepository<MembershipJpaEntity, Long> {

    @Query("SELECT m " +
            "FROM MembershipJpaEntity m " +
            "WHERE m.address = :address")
     List<MembershipJpaEntity> findByAddress(@Param("address") String address);


}
