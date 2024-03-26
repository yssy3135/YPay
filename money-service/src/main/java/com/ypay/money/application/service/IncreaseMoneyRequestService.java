package com.ypay.money.application.service;

import com.ypay.banking.application.port.out.GetMembershipPort;
import com.ypay.common.CountDownLatchManager;
import com.ypay.common.RechargingMoneyTask;
import com.ypay.common.SubTask;
import com.ypay.common.UseCase;
import com.ypay.money.adapter.axon.command.IncreaseMemberMoneyCommand;
import com.ypay.money.adapter.axon.command.MemberMoneyCreatedCommand;
import com.ypay.money.adapter.out.persistence.MemberMoneyJpaEntity;
import com.ypay.money.adapter.out.persistence.MoneyChangingRequestMapper;
import com.ypay.money.application.port.in.CreateMemberMoneyCommand;
import com.ypay.money.application.port.in.CreateMemberMoneyUseCase;
import com.ypay.money.application.port.in.IncreaseMoneyRequestCommand;
import com.ypay.money.application.port.in.IncreaseMoneyRequestUseCase;
import com.ypay.money.application.port.out.CreateMemberMoneyPort;
import com.ypay.money.application.port.out.GetMemberMoneyPort;
import com.ypay.money.application.port.out.IncreaseMoneyPort;
import com.ypay.money.application.port.out.SendRechargingMoneyTaskPort;
import com.ypay.money.domain.MemberMoney;
import com.ypay.money.domain.MoneyChangingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UseCase
@RequiredArgsConstructor
@Transactional
public class IncreaseMoneyRequestService implements IncreaseMoneyRequestUseCase, CreateMemberMoneyUseCase {

    private final CountDownLatchManager countDownLatchManager;
    private final IncreaseMoneyPort increaseMoneyPort;
    private final MoneyChangingRequestMapper mapper;
    private final GetMembershipPort getMembershipPort;
    private final SendRechargingMoneyTaskPort sendRechargingMoneyTaskPort;
    private final CommandGateway commandGateway;
    private final CreateMemberMoneyPort createMemberMoneyPort;
    private final GetMemberMoneyPort getMemberMoneyPort;


    @Override
    public MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command) {

        // 머니의 충전.증액이라는 과정
        // 1. 고객 정보가 정상인지 확인 (멤버)


        // 2. 고객의 연동된 계좌가 있는지, 고객의 연동된 계좌의 잔액이 충분한지도 확인 (뱅킹)

        // 3. 법인 계좌 상태도 정상인지 확인 (뱅킹)

        // 4. 증액을 위한 "기록". 요청 상태로 MoneyChangingRequest 를 생성한다. (MoneyChangingRequest)

        // 5. 펌뱅킹을 수행하고 (고객의 연동된 계좌 -> 패캠페이 법인 계좌) (뱅킹)

        // 6-1. 결과가 정상적이라면. 성공으로 MoneyChangingRequest 상태값을 변동 후에 리턴
        // 성공 시에 멤버의 MemberMoney 값 증액이 필요

        // 성공이라고 가정
        MemberMoneyJpaEntity memberMoneyJpaEntity = increaseMoneyPort.increaseMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
                ,command.getAmount());

        if(memberMoneyJpaEntity != null) {
            return mapper.mapToDomainEntity(increaseMoneyPort.createMoneyChangingRequest(
                            new MoneyChangingRequest.TargetMembershipId(command.getTargetMembershipId()),
                            new MoneyChangingRequest.MoneyChangingType(1),
                            new MoneyChangingRequest.ChangingMoneyAmount(command.getAmount()),
                            new MoneyChangingRequest.MoneyChangingStatus(1),
                            new MoneyChangingRequest.Uuid(UUID.randomUUID().toString())
                    )
            );
        }

        // 6-2. 결과가 실패라면, 실패라고 MoneyChangingRequest 상태값을 변동 후에 리턴
        return null;
    }

    @Override
    public MoneyChangingRequest increaseMoneyRequestAsync(IncreaseMoneyRequestCommand command) {

        // Subtask
        // 각 서비스에 특정 membershipId로 Validation을 하기위한 Task.

        // 1. Subtask, Task
        SubTask validMemberTask = SubTask.builder()
                .subTaskName("validMemberTask : " + "멤버십 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("membership")
                .status("ready")
                .build();

        SubTask validBankingAccountTask = SubTask.builder()
                .subTaskName("validBankingAccountTask : " + "뱅킹 계좌 유효성 검사")
                .membershipID(command.getTargetMembershipId())
                .taskType("banking")
                .status("ready")
                .build();

        // Banking Sub task
        // Banking Account Validation
        // Amount Money Firmbanking -> 무조건 ok 받았다고 가정..

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(validMemberTask);
        subTaskList.add(validBankingAccountTask);

        RechargingMoneyTask task = RechargingMoneyTask.builder()
                .taskID(UUID.randomUUID().toString())
                .taskName("Increase Money Task / 머니 충전 Task")
                .subTaskList(subTaskList)
                .moneyAmount(command.getAmount())
                .membershipID(command.getTargetMembershipId())
                .toBankName("ybank")
                .build();


        // 2. Kafka Cluster Produce
        // Task Produce
        sendRechargingMoneyTaskPort.sendRechargingMoneyTaskPort(task);
        countDownLatchManager.addCountDownLatch(task.getTaskID());
        // 3. wait
        try {
            countDownLatchManager.getCountDownLatch(task.getTaskID()).await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 3-1. task-consumer
        // 등록된 sub-task, 수행하고 모두 ok -> task 결과를 Produce

        // 4. Task Result Consume
        // 받은 응답을 다시, countDownLatchManger를 통해서 결과 데이터를 받아야한다.
        String result = countDownLatchManager.getDataForKey(task.getTaskID());
        if(result.equals("success")) {
            // 4-1. Consume ok, Logic

        } else {
            // 4-2/ Consume fail Logic
        }
        // 5. Consume ok, Logic


        return null;
    }

    @Override
    public void createMemberMoney(CreateMemberMoneyCommand command) {
        MemberMoneyCreatedCommand axonCommand = new MemberMoneyCreatedCommand(command.getMembershipId());

        commandGateway.send(axonCommand).whenComplete((result, throwable) -> {
           if(throwable != null ) {
               System.out.println("throwable = " + throwable);

               throw new RuntimeException(throwable);
           } else {
               createMemberMoneyPort.createMemberMoney(
                       new MemberMoney.MembershipId(command.getMembershipId()),
                       new MemberMoney.MoneyAggregateIdentifier(result.toString())
               );
           }

        });
    }

    @Override
    public void increaseMoneyRequestByEvent(IncreaseMoneyRequestCommand command) {
        MemberMoneyJpaEntity memberMoneyJpaEntity = getMemberMoneyPort.getMemberMoney(
                new MemberMoney.MembershipId(command.getTargetMembershipId())
        );

        String aggregateIdentifier = memberMoneyJpaEntity.getAggregateIdentifier();

        commandGateway.send(IncreaseMemberMoneyCommand.builder()
                        .aggregateIdentifier(aggregateIdentifier)
                        .membershipId(command.getTargetMembershipId())
                        .amount(command.getAmount()).build())
                .whenComplete(
                        (result, throwable) -> {
                            if (throwable != null) {
                                throwable.printStackTrace();
                                throw new RuntimeException(throwable);
                            } else {
                                // Increase money -> money incr
                                System.out.println("increaseMoney result = " + result);
                                increaseMoneyPort.increaseMoney(
                                        new MemberMoney.MembershipId(command.getTargetMembershipId())
                                        , command.getAmount());
                            }
                        }
                );
    }

}



