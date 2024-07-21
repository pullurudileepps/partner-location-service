package com.miniproject.allocation.strategy;

import org.springframework.stereotype.Component;
import com.miniproject.partnerlocation.model.Location;

@Component
public class HaversineStrategy implements CalculateShortestDistance{
    @Override
    public double calculateShortestDistance(Location orderLocation, Location partnerLocation) {
        final int EARTH_RADIUS = 6371; // Earth radius in kilometers

        double latitude1 = orderLocation.getLatitude();
        double latitude2 = partnerLocation.getLatitude();

        double longitude1 = orderLocation.getLongitude();
        double longitude2 = partnerLocation.getLongitude();

        double latDistance = Math.toRadians(latitude2 - latitude1);
        double lonDistance = Math.toRadians(longitude2 - longitude1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(longitude1)) * Math.cos(Math.toRadians(longitude2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }
}
