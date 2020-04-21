package com.xabe.redis.consumer.infrastructure.persentation;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.infrastructure.application.PaymentUseCase;
import com.xabe.redis.consumer.infrastructure.persentation.payload.PaymentPayload;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final Logger logger;

  private final PaymentUseCase paymentUseCase;

  @GetMapping
  public ResponseEntity getPayments() {
    this.logger.info("Get all payments");
    return ResponseEntity.ok(this.paymentUseCase.getPayments().stream().map(this::mapper).collect(Collectors.toList()));
  }

  private PaymentPayload mapper(final PaymentDO paymentDO) {
    return PaymentPayload.builder().amount(paymentDO.getAmount()).name(paymentDO.getName())
        .currencyCode(paymentDO.getCurrency().getCurrencyCode()).build();
  }
}
