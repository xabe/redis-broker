package com.xabe.redis.producer.infrastructure.application;

import com.xabe.redis.producer.domain.entity.PaymentDO;
import com.xabe.redis.producer.domain.repository.PaymentRepository;
import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentUseCaseImpl implements PaymentUseCase {

  private final PaymentRepository paymentRepository;

  @Override
  public void createPayment(final PaymentPayload paymentPayload) {
    this.paymentRepository.createPayment(this.mapper(paymentPayload));
  }

  private PaymentDO mapper(final PaymentPayload paymentPayload) {
    return PaymentDO.builder().name(paymentPayload.getName()).amount(paymentPayload.getAmount())
        .currency(Currency.getInstance(paymentPayload.getCurrencyCode())).build();
  }
}
