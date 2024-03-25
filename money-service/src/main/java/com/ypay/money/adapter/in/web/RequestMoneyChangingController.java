package com.ypay.money.adapter.in.web;

import com.ypay.common.WebAdapter;
import com.ypay.money.application.port.in.CreateMemberMoneyCommand;
import com.ypay.money.application.port.in.CreateMemberMoneyUseCase;
import com.ypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.ypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.ypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class RequestMoneyChangingController {

    private final IncreaseMoneyRequestUseCase increaseMoneyRequestUseCase;

    private final CreateMemberMoneyUseCase createMemberMoneyUseCase;


    @PostMapping(path = "/money/increase")
    MoneyChangingResultDetail increaseMoneyChangingRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequest(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }

    @PostMapping(path = "/money/increase-async")
    MoneyChangingResultDetail increaseMoneyChangingAsyncRequest(@RequestBody IncreaseMoneyChangingRequest request) {
        IncreaseMoneyRequestCommand command = IncreaseMoneyRequestCommand.builder()
                .targetMembershipId(request.getTargetMembershipId())
                .amount(request.getAmount())
                .build();

        MoneyChangingRequest moneyChangingRequest = increaseMoneyRequestUseCase.increaseMoneyRequestAsync(command);

        // MoneyChangingRequest -> MoneyChangingResultDetail
        MoneyChangingResultDetail resultDetail = new MoneyChangingResultDetail(
                moneyChangingRequest.getMoneyChangingRequestId(),
                0,
                0,
                moneyChangingRequest.getChangingMoneyAmount()
        );
        return resultDetail;
    }

    @PostMapping(path = "/money/decrease")
    MoneyChangingResultDetail decreaseMoneyChangingRequest(@RequestBody DecreaseMoneyChangingRequest request) {
//        RegisterBankAccountCommand command = RegisterBankAccountCommand.builder()
//                .membershipId(request.getMembershipId())
//                .bankName(request.getBankName())
//                .bankAccountNumber(request.getBankAccountNumber())
//                .isValid(request.isValid())
//                .build();

        // registeredBankAccountUseCase.registerBankAccount(command)
        // -> MoneyChangingResultDetail
        // return decreaseMoneyRequestUseCase.decreaseMoneyChangingRequest(command);
        return null;
    }

    @PostMapping(path = "/money/create-member-money")
    void createMemberMoney(@RequestBody CreateMemberMoneyRequest createMemberMoneyRequest) {
        CreateMemberMoneyCommand createMemberMoneyCommand = CreateMemberMoneyCommand.builder()
                .membershipId(createMemberMoneyRequest.getMembershipId())
                .build();
        createMemberMoneyUseCase.createMemberMoney(createMemberMoneyCommand);
    }



}
