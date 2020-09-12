package com.kosmar.billing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = ChangeMoneyRequest.ChangeMoneyRequestBuilder.class)
class ChangeMoneyRequest {

    private Long userId;
    private BigDecimal amount;

    @JsonPOJOBuilder(withPrefix = "")
    static final class ChangeMoneyRequestBuilder {

    }
}

