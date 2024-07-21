package com.miniproject.partnerlocation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Location implements Serializable {
    private double latitude;
    private double longitude;
}
