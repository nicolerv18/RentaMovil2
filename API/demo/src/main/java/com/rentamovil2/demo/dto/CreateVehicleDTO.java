package com.rentamovil2.demo.dto;

import lombok.Data;

@Data
public class CreateVehicleDTO {
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
