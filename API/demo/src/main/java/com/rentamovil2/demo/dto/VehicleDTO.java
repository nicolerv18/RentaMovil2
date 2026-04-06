package com.rentamovil2.demo.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "brand", "model", "plate", "year", "fuelType", "status", "mileage", "price", "vehicleType", "branchId", "tonnage", "numAxles", "hasTrailer", "numDoors", "transmission", "hasSunroof", "traction", "loadCapacity", "hasArmor"})
public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String plate;
    private Integer year;
    private String fuelType;
    private String status;
    private Double mileage;
    private Double price;
    private String vehicleType;
    private Long branchId;
    private Double tonnage;
    private Integer numAxles;
    private Boolean hasTrailer;
    private Integer numDoors;
    private String transmission;
    private Boolean hasSunroof;
    private String traction;
    private Double loadCapacity;
    private Boolean hasArmor;
}
