package com.rentamovil2.demo.service;
import com.rentamovil2.demo.dto.LoginDTO;
import com.rentamovil2.demo.dto.RegisterDTO;
import com.rentamovil2.demo.dto.UserDTO;


import java.util.List;
public interface UserService {
    UserDTO registerUser(RegisterDTO registrerDTO);
    UserDTO loginUser(LoginDTO loguinDTO);
    UserDTO getById(Long id);
    UserDTO getByUsername(String username);
    List<UserDTO> getAllUsers();
    UserDTO update(Long id, UserDTO userDTO);
    void deleteUser(Long id);

    
}
