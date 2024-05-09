package com.ypay.money.query.application.port.out;

public interface InsertMoneyIncreaseEventByAddress {
    void insertMoneyIncreaseEventByAddress(String addressName, int moneyIncrease);
}
