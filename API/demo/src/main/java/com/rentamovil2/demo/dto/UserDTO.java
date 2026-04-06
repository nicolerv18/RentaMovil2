package com.rentamovil2.demo.dto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({"id", "username", "email", "phone", "userType"})
public class UserDTO {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String userType;
    
}
