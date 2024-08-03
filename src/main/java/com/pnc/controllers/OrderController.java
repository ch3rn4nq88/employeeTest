package com.pnc.controllers;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Builder
    @Data
    static class Order{
        private String customer;
        private LocalDate dob;
        private Integer orderId;
        private List<Integer> productIds;
    }
    @GetMapping(
            value = "/getOrder/{orderId}",
            produces = "application/json")
    public Order getOrder(@PathVariable(name = "orderId") int orderId){

        switch (orderId){
            case 1:
                return Order.builder().orderId(1)
                        .dob(LocalDate.now())
                        .productIds(List.of(1,2,3,4,5,6,7,8))
                        .customer("Carlos Quintana").build();
            case 2:
                return Order.builder().orderId(1)
                        .dob(LocalDate.now())
                        .productIds(List.of(4,5,6,7,8))
                        .customer("Ana Leina").build();
            default:
                return null;
        }

    }


}
