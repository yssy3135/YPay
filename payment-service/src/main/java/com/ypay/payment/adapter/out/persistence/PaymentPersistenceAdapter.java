package com.ypay.payment.adapter.out.persistence;

import com.ypay.common.PersistenceAdapter;
import com.ypay.payment.application.port.out.ChangePaymentRequestStatusPort;
import com.ypay.payment.application.port.out.CreatePaymentPort;
import com.ypay.payment.application.port.out.GetNormalStatusPaymentPort;
import com.ypay.payment.domain.Payment;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements CreatePaymentPort, GetNormalStatusPaymentPort, ChangePaymentRequestStatusPort {
    private final SpringDataPaymentRepository paymentRepository;
    private final PaymentMapper mapper;


    @Override
    public Payment createPayment(String requestMembershipId, String requestPrice, String franchiseId, String franchiseFeeRate) {
        PaymentJpaEntity jpaEntity = paymentRepository.save(
                new PaymentJpaEntity(
                        requestMembershipId,
                        Integer.parseInt(requestPrice),
                        franchiseId,
                        franchiseFeeRate,
                        0, // 0: 승인, 1: 실패, 2: 정산 완료.
                        null
                )
        );
        return mapper.mapToDomainEntity(jpaEntity);
    }

    @Override
    public List<Payment> getNormalStatusPayments() {
        return paymentRepository.findByPaymentStatus(0)
                .stream()
                .map(mapper::mapToDomainEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void changePaymentRequestStatus(String paymentId, int status) {
        paymentRepository.findById(Long.parseLong(paymentId))
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"))
                .setPaymentStatus(status);
    }
}