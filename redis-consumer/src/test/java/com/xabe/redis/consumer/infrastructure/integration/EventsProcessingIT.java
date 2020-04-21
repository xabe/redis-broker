package com.xabe.redis.consumer.infrastructure.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import com.xabe.redis.consumer.App;
import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.infrastructure.persentation.payload.PaymentPayload;
import java.util.Currency;
import java.util.concurrent.TimeUnit;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EventsProcessingIT {

  public static final int TIMEOUT_MS = 3000;

  public static final int DELAY_MS = 500;

  public static final int POLL_INTERVAL_MS = 500;

  @Autowired
  private RedisTemplate<String, PaymentDO> redisTemplate;

  @Autowired
  private ChannelTopic topic;

  @LocalServerPort
  private int serverPort;

  @Test
  public void shouldConsumerPayment() throws Exception {
    //Given
    final PaymentDO paymentDO = PaymentDO.builder().name("chabir").currency(Currency.getInstance("EUR")).amount(1000L).build();

    //When
    this.redisTemplate.convertAndSend(this.topic.getTopic(), paymentDO);

    //Then
    Awaitility.await().pollDelay(DELAY_MS, TimeUnit.MILLISECONDS).pollInterval(POLL_INTERVAL_MS, TimeUnit.MILLISECONDS)
        .atMost(TIMEOUT_MS, TimeUnit.MILLISECONDS).until(() -> {

      final HttpResponse<PaymentPayload[]> response = Unirest.get(String.format("http://localhost:%d/payment", this.serverPort))
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).asObject(PaymentPayload[].class);

      assertThat(response, is(notNullValue()));
      assertThat(response.getStatus(), is(200));
      assertThat(response.getBody().length, is(greaterThanOrEqualTo(1)));
      return true;
    });
  }

}
