package com.xabe.redis.producer.infrastructure.application;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.xabe.redis.producer.domain.entity.PaymentDO;
import com.xabe.redis.producer.domain.repository.PaymentRepository;
import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentUseCaseImplTest {

  private PaymentRepository paymentRepository;

  private PaymentUseCase paymentUseCase;

  @BeforeEach
  public void setUp() throws Exception {
    this.paymentRepository = mock(PaymentRepository.class);
    this.paymentUseCase = new PaymentUseCaseImpl(this.paymentRepository);
  }

  @Test
  public void shouldCreatePayment() throws Exception {
    final PaymentPayload paymentPayload = PaymentPayload.builder().name("pepe").amount(10000L).currencyCode("MAD").build();

    this.paymentUseCase.createPayment(paymentPayload);

    verify(this.paymentRepository)
        .createPayment(eq(PaymentDO.builder().name("pepe").amount(10000L).currency(Currency.getInstance("MAD")).build()));
  }

}