package com.ypay.money.application.port.out;

import com.ypay.common.RechargingMoneyTask;

public interface SendRechargingMoneyTaskPort {
    void sendRechargingMoneyTaskPort(
            RechargingMoneyTask  task
    );
}
