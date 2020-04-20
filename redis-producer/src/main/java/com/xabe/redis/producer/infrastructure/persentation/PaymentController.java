package com.xabe.redis.producer.infrastructure.persentation;

import com.xabe.redis.producer.infrastructure.application.PaymentUseCase;
import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

  private final Logger logger;

  private final PaymentUseCase paymentUseCase;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity createPayment(@Valid @RequestBody final PaymentPayload paymentPayload) {
    this.paymentUseCase.createPayment(paymentPayload);
    this.logger.info("Create payment {}", paymentPayload);
    return ResponseEntity.ok().build();
  }
}
