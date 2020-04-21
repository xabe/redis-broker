package com.xabe.redis.consumer.infrastructure.persentation.payload;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;
import org.springframework.validation.FieldError;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ErrorPayload {

  @Builder.Default
  private final List<FieldValidationErrorPayload> fieldErrors = Collections.emptyList();

  @Value
  @Builder(toBuilder = true)
  @EqualsAndHashCode
  @ToString
  @NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
  @AllArgsConstructor
  public static class FieldValidationErrorPayload {

    private final String field;

    private final String message;

    @Builder(builderMethodName = "builder")
    public static FieldValidationErrorPayloadBuilder builder(final FieldError fieldError) {
      final FieldValidationErrorPayloadBuilder carDTOBuilder = new FieldValidationErrorPayloadBuilder();
      carDTOBuilder.field = fieldError.getField();
      carDTOBuilder.message = fieldError.getDefaultMessage();
      return carDTOBuilder;
    }

  }
}
