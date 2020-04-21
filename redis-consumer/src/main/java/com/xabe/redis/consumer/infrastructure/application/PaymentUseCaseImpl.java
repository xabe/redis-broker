package com.xabe.redis.consumer.infrastructure.application;

import com.xabe.redis.consumer.domain.entity.PaymentDO;
import com.xabe.redis.consumer.domain.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentUseCaseImpl implements PaymentUseCase {

  private final PaymentRepository paymentRepository;

  @Override
  public List<PaymentDO> getPayments() {
    return this.paymentRepository.getPayments();
  }
}
