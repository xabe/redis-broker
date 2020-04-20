package com.xabe.redis.producer.infrastructure.persentation.payload;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PaymentPayload {

  @NotNull
  private final String name;

  @NotNull
  private final Long amount;

  @NotNull
  private final String currencyCode;

}
