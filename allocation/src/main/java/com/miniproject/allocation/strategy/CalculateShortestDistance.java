package com.miniproject.allocation.strategy;

import com.miniproject.partnerlocation.model.Location;

public interface CalculateShortestDistance {

    double calculateShortestDistance(Location orderLocation, Location partnerLocation);
}
