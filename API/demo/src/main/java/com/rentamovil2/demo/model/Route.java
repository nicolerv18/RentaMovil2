package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@ToString(exclude = {"gps", "rental"})
@EqualsAndHashCode(exclude = {"gps", "rental"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "timestamp", nullable = false)
    private Long timestamp;

    @Column(name = "origin", nullable = false)
    private String origin;

    @Column(name = "destination", nullable = false)
    private String destination;

    @ManyToOne
    @JoinColumn(name = "gps_id", nullable = false)
    private Gps gps;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

}

