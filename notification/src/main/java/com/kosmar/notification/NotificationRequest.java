package com.kosmar.notification;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
class NotificationRequest {
    private Long  userId;
    private BigDecimal price;
}
