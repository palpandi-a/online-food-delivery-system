package com.order.service.model.dto;

import java.util.List;

import com.order.service.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private int orderId;

    private int customerId;

    private int restaurantId;

    private String deliveryAddress;

    private Status status;

    private List<OrderedItemsDTO> items;

    private double totalPrice;
}
