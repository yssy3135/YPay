package com.ypay.remittance.application.port.out;


import com.ypay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.ypay.remittance.application.port.in.RequestRemittanceCommand;

public interface RequestRemittancePort {

    RemittanceRequestJpaEntity createRemittanceRequestHistory(RequestRemittanceCommand command);
    boolean saveRemittanceRequestHistory(RemittanceRequestJpaEntity entity);
}
