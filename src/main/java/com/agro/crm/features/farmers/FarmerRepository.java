package com.agro.crm.features.farmers;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Optional<Farmer> findByPhone(String phone);

    List<Farmer> findAllByManagerEmail(String email);

    @EntityGraph(attributePaths = {"fields", "manager"})
    @Query("SELECT DISTINCT f FROM Farmer f")
    List<Farmer> findAllWithFieldsAndManager();

    @EntityGraph(attributePaths = {"fields", "manager"})
    @Query("SELECT DISTINCT f FROM Farmer f WHERE " +
            "LOWER(f.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.phone) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(f.region) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Farmer> searchFarmersWithFieldsAndManager(@Param("search") String search);
}
