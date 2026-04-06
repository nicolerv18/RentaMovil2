package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;


@ToString(exclude = {"route"})
@EqualsAndHashCode(exclude = {"route"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gps")
public class Gps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String model;
    
    @Column(name="available", nullable = false)
    private boolean available;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL)
    private List<Route> route;

}
