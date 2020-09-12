package com.kosmar.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
class OrderFront {
    private String id;
    private Long userId;
    private BigDecimal price;
    private Long version;
    private String status;
}
