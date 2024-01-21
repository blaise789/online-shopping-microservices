package com.globalcoder.orderservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.math.BigDecimal;

@Entity
@Table(name = "t_order_line_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
