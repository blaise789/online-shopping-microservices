package com.dailycodewithme.orderservice.service;

import com.dailycodewithme.orderservice.dto.OrderLineItemsDto;
import com.dailycodewithme.orderservice.dto.OrderRequest;
import com.dailycodewithme.orderservice.model.Order;
import com.dailycodewithme.orderservice.model.OrderLineItems;
import com.dailycodewithme.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
List<OrderLineItems> orderLineItemsList=orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
order.setOrderLineItemsList(orderLineItemsList);
// call inventory serice and place order if the product is in the stock
orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }

}
