package com.ypay.money.query.adapter.axon;

import com.ypay.common.event.RequestFirmbankingFinishedEvent;
import com.ypay.money.query.application.port.out.GetMemberAddressInfoPort;
import com.ypay.money.query.application.port.out.MemberAddressInfo;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class MoneyIncreaseEventHandler {
    @EventHandler
    public void handler(RequestFirmbankingFinishedEvent event, GetMemberAddressInfoPort getMemberAddressInfoPort) {
        System.out.println("Money Increase Event Received: "+ event.toString());


        // 고객의 주소 정보
        MemberAddressInfo memberAddressInfo = getMemberAddressInfoPort.getMemberAddressInfo(event.getMembershipId());

        // Dynamodb Insert!
        String address = memberAddressInfo.getAddress();

        int moneyIncrease = event.getMoneyAmount();
    }
}