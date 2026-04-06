package com.rentamovil2.demo.service.impl;

import com.rentamovil2.demo.dto.LoginDTO;
import com.rentamovil2.demo.dto.RegisterDTO;
import com.rentamovil2.demo.dto.UserDTO;
import com.rentamovil2.demo.model.Customer;
import com.rentamovil2.demo.model.User;
import com.rentamovil2.demo.repository.UserRepository;
import com.rentamovil2.demo.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.existsByPhone(registerDTO.getPhone())) {
            throw new RuntimeException("Phone number already in use");
        }
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Username already in use");
        }

        Customer customer = new Customer(
            registerDTO.getUsername(),
            registerDTO.getEmail(),
            registerDTO.getPhone(),
            registerDTO.getPassword()
        );

        User saved = userRepository.save(customer);
        return toDTO(saved);
    }

    @Override
    public UserDTO loginUser(LoginDTO dto) {
        User user = userRepository.findByEmailOrPhone(
            dto.getIdentifier(),
            dto.getIdentifier()
        ).orElseThrow(() -> new RuntimeException("User not found"));

        if (!dto.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }
        return toDTO(user);
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    @Override
    public UserDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
            .stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public UserDTO update(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        return toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setUserType(user instanceof Customer ? "Customer" : "Admin"); //para poder mostrar el tipo de usuairio en el frontend
        return dto;
    }
}