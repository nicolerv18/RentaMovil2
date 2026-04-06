package com.rentamovil2.demo.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id_admin")
@DiscriminatorValue("admin")
public class Admin extends User {
    public Admin(String username, String email, String phone, String password) {
        super(null, username, email, phone, password);
    }
}