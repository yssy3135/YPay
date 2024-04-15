package com.ypay.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ypay.common.CommonHttpClient;
import com.ypay.money.aggregation.application.port.out.GetMembershipPort;
import com.ypay.money.aggregation.application.port.out.MemberMoney;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MembershipServiceAdapter implements GetMembershipPort {


    private final CommonHttpClient commonHttpClient;

    private final String membershipServiceUrl;

    public MembershipServiceAdapter(CommonHttpClient commonHttpClient,
                                    @Value("${service.membership.url}") String membershipServiceUrl) {
        this.commonHttpClient = commonHttpClient;
        this.membershipServiceUrl = membershipServiceUrl;
    }

    @Override
    public List<String> getMembershipByAddress(String address) {
        String url = String.join("/", membershipServiceUrl, "membership/address", address);
        try {
            String jsonResponse = commonHttpClient.sendGetRequest(url).body();
            // json Membership

            ObjectMapper mapper = new ObjectMapper();
            List<Membership> membershipList = mapper.readValue(jsonResponse, new TypeReference<>() {});

            return membershipList.stream()
                    .map(Membership::getMembershipId)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}