package com.xabe.redis.producer.infrastructure.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.xabe.redis.producer.App;
import com.xabe.redis.producer.domain.entity.PaymentDO;
import com.xabe.redis.producer.infrastructure.integration.config.PaymentSubscriber;
import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EventsProcessingIT {

  private static final long DEFAULT_TIMEOUT_MS = 3000;

  @Autowired
  private PaymentSubscriber paymentSubscriber;

  @LocalServerPort
  private int serverPort;

  @BeforeEach
  public void init() {
    this.paymentSubscriber.before();
  }

  @Test
  public void shouldCreatedPayment() throws Exception {
    final PaymentPayload paymentPayload = PaymentPayload.builder().name("chabir").amount(1000L).currencyCode("EUR").build();

    final HttpResponse<JsonNode> response = Unirest.post(String.format("http://localhost:%d/payment", this.serverPort))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).body(paymentPayload).asJson();

    assertThat(response, is(notNullValue()));
    assertThat(response.getStatus(), is(200));

    final PaymentDO result = this.paymentSubscriber.expectMessage(DEFAULT_TIMEOUT_MS);
    assertThat(result, is(notNullValue()));
  }

}
