package br.com.wealth.core.repositories;

import br.com.wealth.core.entities.TenantAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantAdminRepository extends JpaRepository<TenantAdmin, Long> {
}
