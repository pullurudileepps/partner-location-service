package com.miniproject.allocation.scheduler;


import com.miniproject.orderpickuplocation.models.Order;
import com.miniproject.partnerlocation.model.Partner;
import com.miniproject.allocation.repository.RedisRepository;
import com.miniproject.allocation.services.AllocateTasks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AllocationScheduler {

    private final RedisRepository redisRepository;
    private final AllocateTasks allocateTasks;

    @Autowired
    public AllocationScheduler(AllocateTasks allocateTasks, RedisRepository redisRepository) {
        this.allocateTasks = allocateTasks;
        this.redisRepository = redisRepository;
    }

    @Scheduled(cron = "0 */5 * * * *") // runs at every 5 minutes
    public void allocationTasks() {
        System.out.println("CRON task starting...Trying to allocate tasks to nearest Partner");
        Map<String, Partner> partnerMap = convertEntriesToPartner(redisRepository.findAll("Partner"));
        Map<String, Order> orderMap = convertEntriesToOrders(redisRepository.findAll("ORDERS"));
        List<Partner> partners = convertToList(partnerMap);
        List<Order> orders = convertToList(orderMap);
        Map<Long, Long> result = this.allocateTasks.allocateTasks(orders, partners);
        this.allocateTasks.saveTasks(result);
        System.out.println("CRON task ended, Tasks are assigned to nearest Partner");
    }
    private Map<String, Order> convertEntriesToOrders(Map<String, Object> orders) {
        Map<String, Order> orderMap = new HashMap<>();
        for(Map.Entry<String,Object> entry : orders.entrySet()){
            orderMap.put(entry.getKey(), (Order) entry.getValue());
        }
        return orderMap;
    }
    private Map<String, Partner> convertEntriesToPartner(Map<String, Object> partners) {
        Map<String, Partner> partnerMap = new HashMap<>();
        for(Map.Entry<String,Object> entry : partners.entrySet()){
            partnerMap.put(entry.getKey(), (Partner) entry.getValue());
        }
        return partnerMap;
    }
    private <T> List<T> convertToList(Map<String, T> entries){
        List<T> result = new ArrayList<>();
        for(Map.Entry<String, T> entry : entries.entrySet()){
            result.add(entry.getValue());
        }
        return result;
    }
}
