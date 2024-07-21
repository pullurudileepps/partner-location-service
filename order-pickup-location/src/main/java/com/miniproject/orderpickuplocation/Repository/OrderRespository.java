package com.miniproject.orderpickuplocation.Repository;

import com.miniproject.orderpickuplocation.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRespository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderById(Long id);
}
