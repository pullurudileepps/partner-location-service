package com.miniproject.allocation.services;

import com.miniproject.allocation.models.Allocation;
import com.miniproject.allocation.repository.AllocationRespository;
import com.miniproject.allocation.strategy.CalculateShortestDistance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.miniproject.orderpickuplocation.models.Order;
import com.miniproject.partnerlocation.model.Partner;
import com.miniproject.partnerlocation.model.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AllocateTasksImpl implements AllocateTasks {
    private final CalculateShortestDistance calculateShortestDistance;

    private final AllocationRespository allocationRespository;

    @Autowired
    public AllocateTasksImpl(AllocationRespository allocationRespository, CalculateShortestDistance calculateShortestDistance) {
        this.allocationRespository = allocationRespository;
        this.calculateShortestDistance = calculateShortestDistance;
    }

    @Override
    public Map<Long, Long> allocateTasks(List<Order> orders, List<Partner> partnerLocations) {
        Map<Long,Long> partnerTaskMap = new HashMap<>();
        for(Order order : orders) {
            double shortestDistance = Double.MAX_VALUE;
            Partner nearestLocation = null;
            for (Partner partnerLocation : partnerLocations) {
                //OrderLocation
                Location order_Location = new Location();
                order_Location.setLatitude(order.getLatitude());
                order_Location.setLongitude(order.getLongitude());
                //PartnerLocation
                Location partner_Location = partnerLocation.getLocation();
                double distance = this.calculateShortestDistance.calculateShortestDistance(order_Location, partner_Location);
                if (distance < shortestDistance) {
                    nearestLocation = partnerLocation;
                    shortestDistance = distance;
                }
            }
            if (nearestLocation != null) {
                partnerTaskMap.put(order.getId(), nearestLocation.getId());
                partnerLocations.remove(nearestLocation);
            }
        }
        return partnerTaskMap;
    }

    @Override
    public void saveTasks(Map<Long, Long> result) {
        for(Map.Entry<Long,Long> entry : result.entrySet()){
            Allocation allocation = new Allocation();
            allocation.setOrderId(entry.getKey());
            allocation.setPartnerId(entry.getValue());
            this.allocationRespository.save(allocation);
        }
    }
}
