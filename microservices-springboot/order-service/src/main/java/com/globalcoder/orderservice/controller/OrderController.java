package com.globalcoder.orderservice.controller;

import com.globalcoder.orderservice.dto.OrderRequest;
import com.globalcoder.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@CircuitBreaker(name = "inventory",fallbackMethod = "fallBackMethod")
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private   OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @TimeLimiter(name = "inventory")
    @Retry(name ="inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
      return   CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }
    public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest,RuntimeException runtimeException){
        return  CompletableFuture.supplyAsync(()->"Oops something went wrong please order after some time!");

    }

}
