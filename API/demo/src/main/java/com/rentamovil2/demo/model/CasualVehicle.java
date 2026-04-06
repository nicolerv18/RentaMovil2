package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "casual_vehicle")
@PrimaryKeyJoinColumn(name = "id_casual_vehicle")
@DiscriminatorValue("casual")
public class CasualVehicle extends Vehicle {

    private Integer numDoors;
    private String transmission;
    private Boolean hasSunroof;
}