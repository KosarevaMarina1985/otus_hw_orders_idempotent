package com.kosmar.billing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = CreateAccountRequest.CreateAccountRequestBuilder.class)
class CreateAccountRequest {

    private Long userId;

    @JsonPOJOBuilder(withPrefix = "")
    static final class CreateAccountRequestBuilder {

    }
}

