package com.ypay.banking.application.port.out;

import com.ypay.banking.adapter.out.external.bank.ExternalFirmbankingRequest;
import com.ypay.banking.adapter.out.external.bank.FirmbankingResult;

public interface RequestExternalFirmbankingPort {

    FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest externalFirmbankingRequest);

}
