package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "heavy_vehicle")
@PrimaryKeyJoinColumn(name = "id_heavy_vehicle")
@DiscriminatorValue("heavy")
public class HeavyVehicle extends Vehicle {

    private Double tonnage;
    private Integer numAxles;
    private Boolean hasTrailer;
}