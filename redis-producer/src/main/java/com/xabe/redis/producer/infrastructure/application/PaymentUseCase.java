package com.xabe.redis.producer.infrastructure.application;

import com.xabe.redis.producer.infrastructure.persentation.payload.PaymentPayload;

public interface PaymentUseCase {

  void createPayment(PaymentPayload paymentPayload);
}
