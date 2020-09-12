package com.kosmar.notification;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
class NotificationFront {
    private Long id;
    private Long userId;
    private String orderId;
    private BigDecimal price;
    private String orderType;
    private String message;
    private LocalDateTime datetime;
}
