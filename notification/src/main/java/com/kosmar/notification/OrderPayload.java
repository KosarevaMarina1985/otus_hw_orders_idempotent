package com.kosmar.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class OrderPayload{
    private Long userId;
    private BigDecimal price;
    private String status;
}
