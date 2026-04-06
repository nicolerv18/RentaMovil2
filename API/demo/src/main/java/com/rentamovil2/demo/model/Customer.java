package com.rentamovil2.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "id_customer")
@DiscriminatorValue("customer")
public class Customer extends User {
    public Customer(String username, String email, String phone, String password) {
        super(null, username, email, phone, password);
    }

}