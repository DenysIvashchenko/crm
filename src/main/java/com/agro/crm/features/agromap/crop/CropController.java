package com.agro.crm.features.agromap.crop;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/crops")
@RequiredArgsConstructor
public class CropController {

    private final CropService cropService;

    @PostMapping("/{fieldId}/fields")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public Crop plant(@PathVariable Long fieldId, @RequestBody Crop crop) {
        return cropService.plant(fieldId, crop);
    }

    // PUT /api/crops/1/harvest?actualYield=4.5 — записать урожай
    @PutMapping("/{id}/harvest")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public Crop harvest(@PathVariable Long id, @RequestParam Double actualYield) {
        return cropService.harvest(id, actualYield);
    }

    // GET /api/crops/report?season=2025
    @GetMapping("/report")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','MANAGER')")
    public List<Crop> report(@RequestParam String season) {
        return cropService.getSeasonReport(season);
    }

    // GET /api/fields/1/crops
    @GetMapping("/{fieldId}/fields")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','MANAGER')")
    public List<Crop> byField(@PathVariable Long fieldId) {
        return cropService.getByField(fieldId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        cropService.delete(id);
    }
}
