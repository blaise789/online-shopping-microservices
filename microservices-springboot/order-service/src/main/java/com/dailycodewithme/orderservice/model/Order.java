package com.dailycodewithme.orderservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "t_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private  String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "order_id",
            referencedColumnName = "id"
    )
    private List<OrderLineItems> orderLineItemsList;

}
