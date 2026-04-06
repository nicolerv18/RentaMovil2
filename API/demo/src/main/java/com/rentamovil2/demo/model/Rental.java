package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.List;

@ToString(exclude = {"customer", "vehicle", "reservation", "gps", "routes"})
@EqualsAndHashCode(exclude = {"customer", "vehicle", "reservation", "gps", "routes"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;


    @OneToOne
    @JoinColumn(name = "gps_id")
    private Gps gps;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL)
    private List<Route> routes;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}