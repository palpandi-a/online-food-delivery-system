package com.delivery.service.model.dto.order;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Status status;

}
