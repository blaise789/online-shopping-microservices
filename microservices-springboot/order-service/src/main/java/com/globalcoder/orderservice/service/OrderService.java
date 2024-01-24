package com.globalcoder.orderservice.service;

import brave.Span;
import brave.Tracer;
import com.globalcoder.orderservice.dto.InventoryResponse;
import com.globalcoder.orderservice.dto.OrderLineItemsDto;
import com.globalcoder.orderservice.dto.OrderRequest;
import com.globalcoder.orderservice.event.OrderPlacedEvent;
import com.globalcoder.orderservice.model.Order;
import com.globalcoder.orderservice.model.OrderLineItems;
import com.globalcoder.orderservice.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class OrderService {
    @Autowired
    private Tracer tracer;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired

    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    @Autowired
    private WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);
        List<String> skuCodes = orderLineItemsList.stream().map(OrderLineItems::getSkuCode).toList();
        Span inventoryServiceLookup = tracer.nextSpan().name("inventory service lookup");
        try (Tracer.SpanInScope spanInScope = tracer.withSpanInScope(inventoryServiceLookup.start())) {
            log.info("calling inventory service");
            InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();
//        System.out.println(inventoryResponseArray);
            boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
            if (allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
                return "order placed successfully";
            } else {
                throw new IllegalArgumentException("The product is not in stock. Please try again later.");
            }




        } finally {
            inventoryServiceLookup.abandon();
        }



    }
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
