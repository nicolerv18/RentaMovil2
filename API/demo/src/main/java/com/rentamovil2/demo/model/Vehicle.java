package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@ToString(exclude = {"branch", "maintenances", "insurance"})
@EqualsAndHashCode(exclude = {"branch", "maintenances", "insurance"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "brand", nullable = false, unique = true)
    private String brand;
    @Column(name = "model", nullable = false, unique = true)
    private String model;
    @Column(name = "plate", nullable = false, unique = true)
    private String plate;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "fuelType", nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Double mileage;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<Maintenance> maintenances;

    @OneToOne(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private Insurance insurance;

}