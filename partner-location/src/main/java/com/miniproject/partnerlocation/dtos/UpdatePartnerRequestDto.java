package com.miniproject.partnerlocation.dtos;

import com.miniproject.partnerlocation.model.Location;
import lombok.Data;

@Data
public class UpdatePartnerRequestDto {
    private String name;
    private Location location;
}
