package com.agro.crm.features.equipment;

import com.agro.crm.features.equipment.dto.EquipmentDto;
import com.agro.crm.features.equipment.dto.EquipmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST')")
    public List<EquipmentResponseDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN','AGRONOMIST','OPERATOR')")
    public List<EquipmentResponseDto> getAvailable() {
        return service.getAvailable();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN','AGRONOMIST')")
    public EquipmentResponseDto create(@RequestBody EquipmentDto equipment) {
        return service.create(equipment);
    }

    // PATCH /api/equipment/1/status?status=MAINTENANCE
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    public Equipment changeStatus(
            @PathVariable Long id,
            @RequestParam EquipmentStatus status) {
        return service.changeStatus(id, status);
    }

    // POST /api/equipment/1/log — оператор фиксирует пробег
    @PostMapping("/{id}/log")
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    public EquipmentLog addLog(
            @PathVariable Long id,
            @RequestBody EquipmentLog log) {
        return service.addLog(id, log);
    }
}
