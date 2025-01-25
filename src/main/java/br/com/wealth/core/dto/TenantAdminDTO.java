package br.com.wealth.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TenantAdminDTO extends UserRegistrationDTO {

    private Long id;
    private String cnpj;
    private String address;
    private Boolean active;

}
