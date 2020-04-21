package com.xabe.redis.consumer.infrastructure.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import java.util.Arrays;
import java.util.List;
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
  public void shouldGetPayments() throws Exception {

    when(this.paymentRepository.getPayments()).thenReturn(Arrays.asList(PaymentDO.builder().build()));

    final List<PaymentDO> result = this.paymentUseCase.getPayments();

    assertThat(result, is(notNullValue()));
  }

}