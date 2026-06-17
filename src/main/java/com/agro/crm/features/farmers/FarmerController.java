package com.agro.crm.features.farmers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farmers")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Farmer create(@RequestBody FarmerCreateRequest dto) {
        return farmerService.createFarmer(dto);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public List<Farmer> list(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "status", required = false) FarmerStatus status
    ) {
        return farmerService.getAll(FarmerFilter.of(search, region, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Farmer getOne(@PathVariable Long id) {
        return farmerService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public Farmer update(@PathVariable Long id, @RequestBody FarmerCreateRequest dto) {
        return farmerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        farmerService.delete(id);
    }
}
