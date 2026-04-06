package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.util.List;

@ToString(exclude = {"vehicle"})
@EqualsAndHashCode(exclude = {"vehicle"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "address", nullable = false, unique = true)
    private String address;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "schedule", nullable = false)
    private String schedule;
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicle;
}
