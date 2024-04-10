package com.ypay.remittance.application.service;


import com.ypay.common.UseCase;
import com.ypay.remittance.adapter.out.persistence.RemittanceRequestMapper;
import com.ypay.remittance.application.port.in.FindRemittanceCommand;
import com.ypay.remittance.application.port.in.FindRemittanceUseCase;
import com.ypay.remittance.application.port.out.FindRemittancePort;
import com.ypay.remittance.domain.RemittanceRequest;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
public class FindRemittanceService implements FindRemittanceUseCase {
    private final FindRemittancePort findRemittancePort;
    private final RemittanceRequestMapper mapper;

    @Override
    public List<RemittanceRequest> findRemittanceHistory(FindRemittanceCommand command) {
        //
        findRemittancePort.findRemittanceHistory(command);
        return null;
    }
}
