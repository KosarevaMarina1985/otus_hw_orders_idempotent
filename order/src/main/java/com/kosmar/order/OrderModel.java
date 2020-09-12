package com.kosmar.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class OrderModel {

    @Id
    private String id;
    @Version private Long version;
    @Column
    private Long userId;
    @Column
    private BigDecimal price;
    @Column
    private String status;
}
