package com.rentamovil2.demo.service;

import com.rentamovil2.demo.dto.CreateVehicleDTO;
import com.rentamovil2.demo.dto.VehicleDTO;

import java.util.List;

public interface VehicleService {
    VehicleDTO createVehicle(CreateVehicleDTO dto);
    VehicleDTO getById(Long id);
    List<VehicleDTO> getAllVehicles();
    VehicleDTO updateVehicle(Long id, CreateVehicleDTO dto);
    void deleteVehicle(Long id);
}
