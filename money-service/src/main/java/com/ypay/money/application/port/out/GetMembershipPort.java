package com.ypay.money.application.port.out;


import com.ypay.banking.application.port.out.MembershipStatus;

public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);

}
