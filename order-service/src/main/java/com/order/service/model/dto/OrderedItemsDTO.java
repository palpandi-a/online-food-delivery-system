package com.order.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedItemsDTO {
    
    private int id;

    private String name;

    private double price;

    private int quantity;

}
