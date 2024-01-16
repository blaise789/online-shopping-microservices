package com.dailycodewithme.orderservice.repository;

import com.dailycodewithme.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
