package com.miniproject.allocation.services;


import com.miniproject.orderpickuplocation.models.Order;
import com.miniproject.partnerlocation.model.Partner;

import java.util.List;
import java.util.Map;

public interface AllocateTasks {
    Map<Long, Long> allocateTasks(List<Order> orders, List<Partner> partnerLocations);

    void saveTasks(Map<Long, Long> result);
}
