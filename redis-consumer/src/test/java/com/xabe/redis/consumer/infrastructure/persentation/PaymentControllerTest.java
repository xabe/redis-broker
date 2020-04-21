package com.xabe.redis.consumer.infrastructure.persentation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.infrastructure.application.PaymentUseCase;
import com.xabe.redis.consumer.infrastructure.persentation.payload.PaymentPayload;
import java.util.Arrays;
import java.util.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class PaymentControllerTest {

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
  public void shouldGetPayments() throws Exception {

    when(this.paymentUseCase.getPayments())
        .thenReturn(Arrays.asList(PaymentDO.builder().amount(1L).name("chabir").currency(Currency.getInstance("EUR")).build()));

    final ResponseEntity result = this.paymentController.getPayments();

    assertThat(result, is(notNullValue()));
    assertThat(result.getStatusCode(), is(HttpStatus.OK));
    assertThat(result.getBody(),
        is(equalTo(Arrays.asList(PaymentPayload.builder().amount(1L).currencyCode("EUR").name("chabir").build()))));
  }

}