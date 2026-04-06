package com.rentamovil2.demo.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentamovil2.demo.model.Branch;
import com.rentamovil2.demo.model.Vehicle;

public class VecihleRepository {
    @Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByPlate(String plate);
    List<Vehicle> findByStatus(String status);
    List<Vehicle> findByBranch(Branch branch);
    boolean existsByPlate(String plate);
}
    
}
