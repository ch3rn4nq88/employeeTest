package com.pnc.controllers;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Builder
    @Data
    static class Product{
        private String name;
        private Integer quantity;
        private BigDecimal value;
    }
    @GetMapping(
            value = "/getProductsById/{productId}",
            produces = "application/json")
    public Product getProduct(@PathVariable(name = "productId") Integer productId){

        Product.ProductBuilder builder = Product.builder();
        switch(productId){
            case 1:
                builder.name("Spinach").quantity(2).value(new BigDecimal(11));
                break;
            case 2:
                builder.name("Carrots").quantity(45).value(new BigDecimal(12));
                break;
            case 3:
                builder.name("Beef").quantity(12).value(new BigDecimal(70));
                break;
            case 4:
                builder.name("Chicken").quantity(11).value(new BigDecimal(6));
                break;
            case 5:
                builder.name("Olive oil").quantity(7).value(new BigDecimal(5));
                break;
            case 6:
                builder.name("Olives").quantity(120).value(new BigDecimal(12));
                break;
            case 7:
                builder.name("Pop corn").quantity(5).value(new BigDecimal(1));
                break;
            default:
                builder.name("Gillete match 3").quantity(5).value(new BigDecimal(1));
                break;
        }

        Product p =builder.build();
        System.out.println(p.getName());
        return p;



    }


}
