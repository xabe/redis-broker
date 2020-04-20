package com.xabe.redis.producer.infrastructure.persentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.xabe.redis.producer.infrastructure.application.PaymentUseCase;
import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PaymentControllerTest {

  private Logger logger;

  private PaymentUseCase paymentUseCase;

  private PaymentController paymentController;

  @BeforeEach
  public void setUp() throws Exception {
    this.logger = mock(Logger.class);
    this.paymentUseCase = mock(PaymentUseCase.class);
    this.paymentController = new PaymentController(this.logger, this.paymentUseCase);
  }

  @Test
  public void shouldCreatePayment() throws Exception {
    final PaymentPayload paymentPayload = PaymentPayload.builder().amount(2990L).name("chabir").currencyCode("MAD").build();

    final ResponseEntity result = this.paymentController.createPayment(paymentPayload);

    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(HttpStatus.OK));
    verify(this.paymentUseCase).createPayment(eq(paymentPayload));
  }
}
