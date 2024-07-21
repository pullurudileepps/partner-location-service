package com.miniproject.orderpickuplocation.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "OrderLocation")
public class Order {
    @Id
    private long id;
    private double latitude;
    private double longitude;
}
