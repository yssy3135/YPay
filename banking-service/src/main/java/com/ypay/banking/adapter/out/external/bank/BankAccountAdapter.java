package com.ypay.banking.adapter.out.external.bank;

import com.ypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.ypay.banking.application.port.out.RequestExternalFirmbankingPort;
import com.ypay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort, RequestExternalFirmbankingPort {




    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {

        // 실제로 외부 은행에 http를 통해서
        // 실제 은행 계좌 정보를 가져오고

        // 실제 은행 계좌 -> BankAccount


        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }

    @Override
    public FirmbankingResult requestExternalFirmbanking(ExternalFirmbankingRequest externalFirmbankingRequest) {
        // 실제로 외부 은행에 http 통신을 통해서
        // 펌뱅킹 요청을 하고

        // 외부 은행의 실제 결과를 -> FirmbankingResult로 파싱
        FirmbankingResult result = new FirmbankingResult(0);

        return result;

    }
}
