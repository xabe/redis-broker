package com.xabe.redis.consumer.infrastructure.persistence;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryPaymentRepositoryTest {

  private PaymentRepository paymentRepository;

  @BeforeEach
  public void setUp() throws Exception {
    this.paymentRepository = new InMemoryPaymentRepository();
  }

  @Test
  public void shouldAddPayments() throws Exception {
    final PaymentDO paymentDO = PaymentDO.builder().build();

    this.paymentRepository.save(paymentDO);

    final List<PaymentDO> result = this.paymentRepository.getPayments();

    assertThat(result, is(notNullValue()));
    assertThat(result, is(hasSize(1)));
  }

}