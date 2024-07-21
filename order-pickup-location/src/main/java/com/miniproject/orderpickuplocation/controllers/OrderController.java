package com.miniproject.orderpickuplocation.controllers;

import com.miniproject.orderpickuplocation.dtos.UpdatePickupLocationRequestDTO;
import com.miniproject.orderpickuplocation.exceptions.InvalidLatitudeException;
import com.miniproject.orderpickuplocation.exceptions.InvalidLongitudeException;
import com.miniproject.orderpickuplocation.models.Order;
import com.miniproject.orderpickuplocation.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{id}/pickup-location")
    public ResponseEntity<Order> CreateOrder(@PathVariable("id") long orderId,
                             @RequestBody UpdatePickupLocationRequestDTO requestDto) {
        try {
            Double latitude = (Double) requestDto.getLatitude();
            Double longitude = (Double) requestDto.getLongitude();
            if (!validateLatitudeCheck(latitude)) {
                throw new InvalidLatitudeException("Value should be between -90 to +90");
            }
            if (!validateLongitudeCheck(longitude)) {
                throw new InvalidLongitudeException("Value should be between -180 to +180");
            }
            Order order = this.orderService.updatePickupLocation(orderId, latitude, longitude);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private boolean validateLatitudeCheck(Double latitude) {
        if (latitude == null) return false;
        return latitude >= -90 && latitude <= 90;
    }

    private boolean validateLongitudeCheck(Double longitude) {
        if (longitude == null) return false;
        return longitude >= -180 && longitude <= 180;
    }
}
