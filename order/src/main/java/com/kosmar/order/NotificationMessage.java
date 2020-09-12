package com.kosmar.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = NotificationMessage.NotificationMessageBuilder.class)
public
class NotificationMessage {

    private Long messageId;
    private String orderId;
    private String type;
    private String payload;

    @JsonPOJOBuilder(withPrefix = "")
    static final class NotificationMessageBuilder {

    }
}
