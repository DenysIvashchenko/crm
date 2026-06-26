package com.agro.crm.features.farmers;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> , JpaSpecificationExecutor<Farmer> {
    Optional<Farmer> findByPhone(String phone);

    List<Farmer> findAllByManagerEmail(String email);

    @EntityGraph(attributePaths = {"fields", "manager"})
    @Query("SELECT DISTINCT f FROM Farmer f")
    List<Farmer> findAllWithFieldsAndManager();

    @Override
    @EntityGraph(attributePaths = {"fields", "manager"})
    List<Farmer> findAll(Specification<Farmer> spec);

    @Query("SELECT SUM(f.totalLandHa) FROM Farmer f")
    Double sumTotalLandAreaHa();
}
