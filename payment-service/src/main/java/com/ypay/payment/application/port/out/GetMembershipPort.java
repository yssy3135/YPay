package com.ypay.payment.application.port.out;


public interface GetMembershipPort {

    public MembershipStatus getMembership(String membershipId);

}
