package com.kosmar.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
class OrderRequest {
    private String orderId;
    private Long  userId;
    private BigDecimal price;
}
