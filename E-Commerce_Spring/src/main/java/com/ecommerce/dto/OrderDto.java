package com.ecommerce.dto;

import com.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class OrderDto {

    private String orderDescription;

    private List<CartItemsDto> cartItems;

    private Long id;

    private Date date;

    private Long amount;

    private String address;

    private OrderStatus status;

    private String payment;

    private String userName;
}
