package com.agro.crm.features.agromap.feild;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGRONOMIST')")
    public Field create(@RequestBody FieldDto dto) {
        return fieldService.createField(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'AGRONOMIST')")
    public Field update(@PathVariable Long id, @RequestBody FieldDto dto) {
        return fieldService.updateField(id, dto);
    }

    @GetMapping
    public List<Field> getAllFields() {
        return fieldService.getAllFields();
    }
    @GetMapping("/farmer/{farmerId}")
    public List<Field> getByFarmer(@PathVariable Long farmerId) {
        return fieldService.getFieldsByFarmer(farmerId);
    }

    @GetMapping("/{id}")
    public Field getOne(@PathVariable Long id) {
        return fieldService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public void delete(@PathVariable Long id) {
        fieldService.deleteField(id);
    }
}
