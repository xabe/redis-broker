package com.xabe.redis.consumer.infrastructure.application;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import java.util.List;

public interface PaymentUseCase {

  List<PaymentDO> getPayments();
}
