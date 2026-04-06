package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalTime;

@ToString(exclude = {"customer", "vehicle", "payment"})
@EqualsAndHashCode(exclude = {"customer", "vehicle", "payment"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation")
public class Reservation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "vehicle_id", nullable = false)
        private Vehicle vehicle;

        @Column(name = "responsibleName", nullable = false)
        private String responsibleName;

        @Column(name = "startDate", nullable = false)
        private LocalDate startDate;

        @Column(name = "endDate", nullable = false)
        private LocalDate endDate;

        @Column(name ="pickupLocation", nullable = false)
        private String pickupLocation;

        @Column(name="returnLocation", nullable = false)
        private String returnLocation;

        @Column(name="pickupTime", nullable = false)
        private LocalTime pickupTime;

        @Column(name="returnTime", nullable = false)
        private LocalTime returnTime;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "payment_id", nullable = false)
        private Payment payment;
}
