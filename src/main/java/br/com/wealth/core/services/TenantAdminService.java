package br.com.wealth.core.services;

import br.com.wealth.core.dto.TenantAdminDTO;

import java.util.List;

public interface TenantAdminService {
    TenantAdminDTO createTenantAdmin(TenantAdminDTO tenantAdminDTO);
    TenantAdminDTO updateTenantAdmin(Long id, TenantAdminDTO tenantAdminDTO);
    TenantAdminDTO getTenantAdminById(Long id);
    List<TenantAdminDTO> getAllTenantAdmins();
    void deleteTenantAdmin(Long id);
    void desactivateTenantAdmin(Long id);
}
