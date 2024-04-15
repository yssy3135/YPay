package com.ypay.money.aggregation.adapter.out.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ypay.common.CommonHttpClient;
import com.ypay.common.ExternalSystemAdapter;
import com.ypay.money.aggregation.application.port.out.GetMoneySumPort;
import com.ypay.money.aggregation.application.port.out.MemberMoney;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class MoneyServiceAdapter implements GetMoneySumPort {


    private final CommonHttpClient moneyServiceHttpClient;

    @Value("${service.money.url}")
    private String moneyServiceEndpoint;

    public MoneyServiceAdapter(CommonHttpClient commonHttpClient,
                               @Value("${service.money.url}") String moneyServiceEndpoint) {
        this.moneyServiceHttpClient = commonHttpClient;
        this.moneyServiceEndpoint = moneyServiceEndpoint;
    }
    @Override
    public List<MemberMoney> getMoneySumByMembershipIds(List<String> membershipIds) {
        String url = String.join("/", moneyServiceEndpoint, "money/member-money");
        ObjectMapper mapper = new ObjectMapper();

        try {
            FindMemberMoneyRequest request = new FindMemberMoneyRequest(membershipIds);
            String jsonResponse = moneyServiceHttpClient.sendPostRequest(url, mapper.writeValueAsString(request)).get().body();
            List<MemberMoney> memberMoneyList = mapper.readValue(jsonResponse, new TypeReference<>() {});
            return memberMoneyList;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
