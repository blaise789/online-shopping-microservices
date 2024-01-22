//package com.dailycodewithme.orderservice.service;
//
//import com.dailycodewithme.orderservice.dto.InventoryResponse;
//import com.dailycodewithme.orderservice.dto.OrderLineItemsDto;
//import com.dailycodewithme.orderservice.dto.OrderRequest;
//import com.dailycodewithme.orderservice.model.Order;
//import com.dailycodewithme.orderservice.model.OrderLineItems;
//import com.dailycodewithme.orderservice.repositories.OrderRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class OrderService {
//
//    //    @Autowired
//    private RestTemplate restTemplate;
//    private  WebClient.Builder webClientBuilder;
//    private   OrderRepository orderRepository;
//
//
//
//
//    public void placeOrder(OrderRequest orderRequest){
//
//     a   Order order=new Order();
//        order.setOrderNumber(UUID.randomUUID().toString());
//        List<OrderLineItems> orderLineItemsList=orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
//        order.setOrderLineItemsList(orderLineItemsList);
//        List<String> skuCodes=orderLineItemsList.stream().map(OrderLineItems::getSkuCode).toList();
//// call inventory serice and place order if the product is in the stock
//        // http://api/inventory/iphone_13,Iphone_2
////        http://localhost:{port}/api/inventory?skuCode="iphone_13"&sku_code="iphone_12"
//// stock
////
//        if(allProductsInStock) {
//            orderRepository.save(order);
//        }
//        else {
//            throw new IllegalArgumentException("the product is not  in the stock,Please try again letter");
//        }
//
//
//    }
//
//    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
//        OrderLineItems orderLineItems = new OrderLineItems();
//        orderLineItems.setPrice(orderLineItemsDto.getPrice());
//        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
//        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
//        return orderLineItems;
//    }
//
//}
