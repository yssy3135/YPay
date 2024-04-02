package com.ypay.money.adapter.axon.saga;


import com.ypay.common.event.CheckRegisteredBankAccountCommand;
import com.ypay.money.adapter.axon.event.RechargingRequestCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@NoArgsConstructor
public class MoneyRechargeSaga {


    @NonNull
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "rechargingRequestId")
    public void handle(RechargingRequestCreatedEvent event) {
        System.out.println("RechargingRequestCreatedEvent Start saga");

        String rechargingRequestId = event.getRechargingRequestId();

        // 다음 동작에서도 동일한 key를 사용하겠다.
        SagaLifecycle.associateWith("rechargingRequestId", rechargingRequestId);

        // "충전 요청"이 시작 되었다.

        // 뱅킹 계좌 등록 여부 확인하기 (RegisteredBankAccount)
        // CheckRegisteredBankAccountCommand -> Check Bank Account
        // -> axon server -> Banking Service -> Common

        // 기본적으로 axon framework에서는, aggregate의 변경은, aggregate단위로 이루어져야 한다.
        commandGateway.send(new CheckRegisteredBankAccountCommand("", rechargingRequestId, event.getMembershipId()));

    }


}
