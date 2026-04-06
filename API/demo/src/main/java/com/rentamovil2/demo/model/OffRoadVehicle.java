package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "offroad_vehicle")
@PrimaryKeyJoinColumn(name = "id_offroad_vehicle")
@DiscriminatorValue("offroad")
public class OffRoadVehicle extends Vehicle {

    private String traction;
    private Double loadCapacity;
    private Boolean hasArmor;
}