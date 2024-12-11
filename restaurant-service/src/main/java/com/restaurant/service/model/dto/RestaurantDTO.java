package com.restaurant.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    
    private int id;

    private String name;

    private String address;

    private String email;

    private String phoneNo;

    private OperatingHoursDTO operatingHours;

}
