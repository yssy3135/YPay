package com.ypay.remittance.application.port.out;


import com.ypay.remittance.adapter.out.persistence.RemittanceRequestJpaEntity;
import com.ypay.remittance.application.port.in.FindRemittanceCommand;

import java.util.List;

public interface FindRemittancePort {

    List<RemittanceRequestJpaEntity> findRemittanceHistory(FindRemittanceCommand command);
}
