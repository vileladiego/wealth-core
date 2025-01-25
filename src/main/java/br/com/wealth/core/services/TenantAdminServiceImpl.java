package br.com.wealth.core.services;

import br.com.wealth.core.dto.TenantAdminDTO;
import br.com.wealth.core.dto.UserRegistrationDTO;
import br.com.wealth.core.entities.Role;
import br.com.wealth.core.entities.TenantAdmin;

import br.com.wealth.core.entities.User;
import br.com.wealth.core.enums.RolesTypeEnum;
import br.com.wealth.core.mappers.TenantAdminMapper;
import br.com.wealth.core.repositories.RoleRepository;
import br.com.wealth.core.repositories.TenantAdminRepository;
import br.com.wealth.core.services.TenantAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TenantAdminServiceImpl implements TenantAdminService {

    @Autowired
    private TenantAdminRepository tenantAdminRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final TenantAdminMapper tenantAdminMapper = TenantAdminMapper.INSTANCE;

    @Override
    public TenantAdminDTO createTenantAdmin(TenantAdminDTO tenantAdminDTO) {
        TenantAdmin entity = TenantAdminMapper.INSTANCE.toEntity(tenantAdminDTO);

        tenantAdminDTO.setRole(RolesTypeEnum.TENANT_ADMIN);

        User user = userService.registerUser(tenantAdminDTO);

        entity.setUser(user);

        TenantAdmin savedEntity = tenantAdminRepository.save(entity);
        return TenantAdminMapper.INSTANCE.toDTO(savedEntity);
    }

    @Override
    public TenantAdminDTO updateTenantAdmin(Long id, TenantAdminDTO tenantAdminDTO) {
        TenantAdmin existingEntity = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TenantAdmin not found with id: " + id));

        updateFieldIfNotNull(tenantAdminDTO.getCnpj(), existingEntity::setCnpj);
        updateFieldIfNotNull(tenantAdminDTO.getAddress(), existingEntity::setAddress);
        updateFieldIfNotNull(tenantAdminDTO.getActive(), existingEntity::setActive);

        TenantAdmin updatedEntity = tenantAdminRepository.save(existingEntity);
        return tenantAdminMapper.toDTO(updatedEntity);
    }

    private <T> void updateFieldIfNotNull(T fieldValue, java.util.function.Consumer<T> setter) {
        if (fieldValue != null) {
            setter.accept(fieldValue);
        }
    }

    @Override
    public TenantAdminDTO getTenantAdminById(Long id) {
        TenantAdmin entity = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TenantAdmin not found with id: " + id));
        return tenantAdminMapper.toDTO(entity);
    }

    @Override
    public List<TenantAdminDTO> getAllTenantAdmins() {
        List<TenantAdmin> entities = tenantAdminRepository.findAll();
        return entities.stream()
                .map(tenantAdminMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTenantAdmin(Long id) {
        if (!tenantAdminRepository.existsById(id)) {
            throw new RuntimeException("TenantAdmin not found with id: " + id);
        }
        tenantAdminRepository.deleteById(id);
    }

    @Override
    public void desactivateTenantAdmin(Long id) {
        TenantAdmin entity = tenantAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TenantAdmin not found with id: " + id));
        entity.setActive(false);
        tenantAdminRepository.save(entity);
    }
}
