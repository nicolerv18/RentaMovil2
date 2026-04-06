package com.rentamovil2.demo.controller;

import com.rentamovil2.demo.dto.CreateVehicleDTO;
import com.rentamovil2.demo.dto.VehicleDTO;
import com.rentamovil2.demo.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> create(@RequestBody CreateVehicleDTO createVehicleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.createVehicle(createVehicleDTO));
    }

    @GetMapping
    public ResponseEntity<List<VehicleDTO>> getAll() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> update(@PathVariable Long id, @RequestBody CreateVehicleDTO createVehicleDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, createVehicleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
