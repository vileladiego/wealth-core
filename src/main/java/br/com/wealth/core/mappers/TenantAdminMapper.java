package br.com.wealth.core.mappers;

import br.com.wealth.core.dto.TenantAdminDTO;
import br.com.wealth.core.entities.Role;
import br.com.wealth.core.entities.TenantAdmin;
import br.com.wealth.core.enums.RolesTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface TenantAdminMapper {

    TenantAdminMapper INSTANCE = Mappers.getMapper(TenantAdminMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cnpj", source = "cnpj")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "confirmPassword", ignore = true)
    @Mapping(target = "role", source = "user.roles", qualifiedByName = "mapRolesToEnum")
    TenantAdminDTO toDTO(TenantAdmin entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cnpj", source = "cnpj")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    @Mapping(target = "user.roles", source = "role", qualifiedByName = "mapEnumToRoles")
    TenantAdmin toEntity(TenantAdminDTO dto);

    @org.mapstruct.Named("mapRolesToEnum")
    default RolesTypeEnum mapRolesToEnum(Set<Role> roles) {
        return roles.stream()
                .findFirst()
                .map(Role::getName)
                .map(RolesTypeEnum::valueOf)
                .orElse(null);
    }

    @org.mapstruct.Named("mapEnumToRoles")
    default Set<Role> mapEnumToRoles(RolesTypeEnum roleEnum) {
        if (roleEnum == null) return Set.of();
        Role role = new Role();
        role.setName(roleEnum.name());
        return Set.of(role);
    }
}
