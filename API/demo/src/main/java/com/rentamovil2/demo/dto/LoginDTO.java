package com.rentamovil2.demo.dto;
import lombok.Data;


@Data
public class LoginDTO {
    private String identifier; //en service se de valida si es email o phone.
    private String password;
    
}
