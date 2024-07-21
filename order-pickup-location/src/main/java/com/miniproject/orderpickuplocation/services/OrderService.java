package com.miniproject.orderpickuplocation.services;

import com.miniproject.orderpickuplocation.models.Order;

public interface OrderService {
    Order updatePickupLocation(long orderId, double latitude, double longitude);
}
