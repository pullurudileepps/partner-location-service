package com.miniproject.orderpickuplocation.services;

import com.miniproject.orderpickuplocation.Repository.OrderRespository;
import com.miniproject.orderpickuplocation.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OrderServiceimpl implements OrderService {

    private final OrderRespository orderRespository;

    private final RedisTemplate<String, Object> redisTemplate;

    private final static String HASH_KEY = "ORDERS";

    @Autowired
    public OrderServiceimpl(OrderRespository orderRespository, RedisTemplate<String, Object> redisTemplate) {
        this.orderRespository = orderRespository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Order updatePickupLocation(long orderId, double latitude, double longitude) {
        Optional<Order> orderIdExist = this.orderRespository.findOrderById(orderId);
        if (orderIdExist.isPresent()) {
            throw new RuntimeException("Order with id " + orderId + " already exists");
        }
        Order order = new Order();
        order.setId(orderId);
        order.setLatitude(latitude);
        order.setLongitude(longitude);
        this.redisTemplate.opsForHash().put(HASH_KEY, "order_" + order.getId(), order);
        return this.orderRespository.save(order);
    }
}