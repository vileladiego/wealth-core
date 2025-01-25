package br.com.wealth.core.dto;



import br.com.wealth.core.enums.RolesTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
public class UserRegistrationDTO {
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String confirmPassword;


    private RolesTypeEnum role;
}
