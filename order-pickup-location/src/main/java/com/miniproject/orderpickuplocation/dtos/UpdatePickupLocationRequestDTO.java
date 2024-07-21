package com.miniproject.orderpickuplocation.dtos;

import lombok.Data;

@Data
public class UpdatePickupLocationRequestDTO {
    private double latitude;
    private double longitude;
}
