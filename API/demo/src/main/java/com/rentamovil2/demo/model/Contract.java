package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@ToString(exclude = {"reservation"})
@EqualsAndHashCode(exclude = {"reservation"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startDate", nullable = false)
    private String startDate;

    @Column(name = "endDate", nullable = false)
    private String endDate;

    @Column(name = "vehicleConditionStart", nullable = false)
    private String vehicleConditionStart;

    @Column(name = "vehicleConditionEnd", nullable = false)
    private String vehicleConditionEnd;

    @Column(name ="termsAndConditions", nullable = false)
    private String termsAndConditions;

    @Column(name = "amountToPay", nullable = false)
    private Double amountToPay;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;
}
