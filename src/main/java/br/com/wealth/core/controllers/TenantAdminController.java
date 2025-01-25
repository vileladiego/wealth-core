package br.com.wealth.core.controllers;

import br.com.wealth.core.dto.TenantAdminDTO;
import br.com.wealth.core.services.TenantAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenant-admins")
public class TenantAdminController {

    @Autowired
    private TenantAdminService tenantAdminService;

    @PostMapping
    public ResponseEntity<TenantAdminDTO> create(@RequestBody TenantAdminDTO tenantAdminDTO) {
        return ResponseEntity.ok(tenantAdminService.createTenantAdmin(tenantAdminDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantAdminDTO> update(@PathVariable Long id, @RequestBody TenantAdminDTO tenantAdminDTO) {
        return ResponseEntity.ok(tenantAdminService.updateTenantAdmin(id, tenantAdminDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantAdminDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantAdminService.getTenantAdminById(id));
    }

    @GetMapping
    public ResponseEntity<List<TenantAdminDTO>> getAll() {
        return ResponseEntity.ok(tenantAdminService.getAllTenantAdmins());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenantAdminService.desactivateTenantAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
