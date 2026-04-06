package com.rentamovil2.demo.service.impl;

import com.rentamovil2.demo.dto.CreateVehicleDTO;
import com.rentamovil2.demo.dto.VehicleDTO;
import com.rentamovil2.demo.model.Branch;
import com.rentamovil2.demo.model.CasualVehicle;
import com.rentamovil2.demo.model.HeavyVehicle;
import com.rentamovil2.demo.model.OffRoadVehicle;
import com.rentamovil2.demo.model.Vehicle;
import com.rentamovil2.demo.repository.BranchRepository;
import com.rentamovil2.demo.repository.VehicleRepository;
import com.rentamovil2.demo.service.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final BranchRepository branchRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, BranchRepository branchRepository) {
        this.vehicleRepository = vehicleRepository;
        this.branchRepository = branchRepository;
    }

    @Override
    public VehicleDTO createVehicle(CreateVehicleDTO dto) {
        Vehicle vehicle = buildVehicle(dto);
        return toDTO(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleDTO getById(Long id) {
        return toDTO(findVehicle(id));
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO updateVehicle(Long id, CreateVehicleDTO dto) {
        Vehicle existing = findVehicle(id);
        if (!matchesVehicleType(existing, dto.getVehicleType())) {
            throw new RuntimeException("Vehicle type cannot be changed during update");
        }
        updateCommonFields(existing, dto);
        updateSubtypeFields(existing, dto);
        if (dto.getBranchId() != null) {
            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            existing.setBranch(branch);
        }
        return toDTO(vehicleRepository.save(existing));
    }

    @Override
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found");
        }
        vehicleRepository.deleteById(id);
    }

    private Vehicle findVehicle(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    private Vehicle buildVehicle(CreateVehicleDTO dto) {
        String type = dto.getVehicleType() == null ? "" : dto.getVehicleType().trim().toLowerCase();
        Vehicle vehicle;
        switch (type) {
            case "heavy":
                vehicle = new HeavyVehicle();
                setHeavyFields((HeavyVehicle) vehicle, dto);
                break;
            case "casual":
                vehicle = new CasualVehicle();
                setCasualFields((CasualVehicle) vehicle, dto);
                break;
            case "offroad":
            case "off-road":
                vehicle = new OffRoadVehicle();
                setOffRoadFields((OffRoadVehicle) vehicle, dto);
                break;
            default:
                throw new RuntimeException("Unsupported vehicle type: " + dto.getVehicleType());
        }
        updateCommonFields(vehicle, dto);
        if (dto.getBranchId() != null) {
            Branch branch = branchRepository.findById(dto.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));
            vehicle.setBranch(branch);
        }
        return vehicle;
    }

    private void updateCommonFields(Vehicle vehicle, CreateVehicleDTO dto) {
        vehicle.setBrand(dto.getBrand());
        vehicle.setModel(dto.getModel());
        vehicle.setPlate(dto.getPlate());
        vehicle.setYear(dto.getYear() != null ? dto.getYear() : 0);
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setStatus(dto.getStatus() != null ? dto.getStatus() : "AVAILABLE");
        vehicle.setMileage(dto.getMileage() != null ? dto.getMileage() : 0.0);
        vehicle.setPrice(dto.getPrice() != null ? dto.getPrice() : 0.0);
    }

    private void updateSubtypeFields(Vehicle vehicle, CreateVehicleDTO dto) {
        if (vehicle instanceof HeavyVehicle) {
            HeavyVehicle heavy = (HeavyVehicle) vehicle;
            if (dto.getTonnage() != null) {
                heavy.setTonnage(dto.getTonnage());
            }
            if (dto.getNumAxles() != null) {
                heavy.setNumAxles(dto.getNumAxles());
            }
            if (dto.getHasTrailer() != null) {
                heavy.setHasTrailer(dto.getHasTrailer());
            }
        } else if (vehicle instanceof CasualVehicle) {
            CasualVehicle casual = (CasualVehicle) vehicle;
            if (dto.getNumDoors() != null) {
                casual.setNumDoors(dto.getNumDoors());
            }
            if (dto.getTransmission() != null) {
                casual.setTransmission(dto.getTransmission());
            }
            if (dto.getHasSunroof() != null) {
                casual.setHasSunroof(dto.getHasSunroof());
            }
        } else if (vehicle instanceof OffRoadVehicle) {
            OffRoadVehicle offRoad = (OffRoadVehicle) vehicle;
            if (dto.getTraction() != null) {
                offRoad.setTraction(dto.getTraction());
            }
            if (dto.getLoadCapacity() != null) {
                offRoad.setLoadCapacity(dto.getLoadCapacity());
            }
            if (dto.getHasArmor() != null) {
                offRoad.setHasArmor(dto.getHasArmor());
            }
        }
    }

    private boolean matchesVehicleType(Vehicle vehicle, String vehicleType) {
        String type = vehicleType == null ? "" : vehicleType.trim().toLowerCase();
        if (vehicle instanceof HeavyVehicle) {
            return "heavy".equals(type);
        }
        if (vehicle instanceof CasualVehicle) {
            return "casual".equals(type);
        }
        if (vehicle instanceof OffRoadVehicle) {
            return "offroad".equals(type) || "off-road".equals(type);
        }
        return false;
    }

    private void setHeavyFields(HeavyVehicle heavy, CreateVehicleDTO dto) {
        heavy.setTonnage(dto.getTonnage());
        heavy.setNumAxles(dto.getNumAxles());
        heavy.setHasTrailer(dto.getHasTrailer());
    }

    private void setCasualFields(CasualVehicle casual, CreateVehicleDTO dto) {
        casual.setNumDoors(dto.getNumDoors());
        casual.setTransmission(dto.getTransmission());
        casual.setHasSunroof(dto.getHasSunroof());
    }

    private void setOffRoadFields(OffRoadVehicle offRoad, CreateVehicleDTO dto) {
        offRoad.setTraction(dto.getTraction());
        offRoad.setLoadCapacity(dto.getLoadCapacity());
        offRoad.setHasArmor(dto.getHasArmor());
    }

    private VehicleDTO toDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setPlate(vehicle.getPlate());
        dto.setYear(vehicle.getYear());
        dto.setFuelType(vehicle.getFuelType());
        dto.setStatus(vehicle.getStatus());
        dto.setMileage(vehicle.getMileage());
        dto.setPrice(vehicle.getPrice());
        Branch branch = vehicle.getBranch();
        dto.setBranchId(branch != null ? branch.getId() : null);

        if (vehicle instanceof HeavyVehicle) {
            HeavyVehicle heavy = (HeavyVehicle) vehicle;
            dto.setVehicleType("Heavy");
            dto.setTonnage(heavy.getTonnage());
            dto.setNumAxles(heavy.getNumAxles());
            dto.setHasTrailer(heavy.getHasTrailer());
        } else if (vehicle instanceof CasualVehicle) {
            CasualVehicle casual = (CasualVehicle) vehicle;
            dto.setVehicleType("Casual");
            dto.setNumDoors(casual.getNumDoors());
            dto.setTransmission(casual.getTransmission());
            dto.setHasSunroof(casual.getHasSunroof());
        } else if (vehicle instanceof OffRoadVehicle) {
            OffRoadVehicle offRoad = (OffRoadVehicle) vehicle;
            dto.setVehicleType("OffRoad");
            dto.setTraction(offRoad.getTraction());
            dto.setLoadCapacity(offRoad.getLoadCapacity());
            dto.setHasArmor(offRoad.getHasArmor());
        } else {
            dto.setVehicleType("Vehicle");
        }
        return dto;
    }
}
