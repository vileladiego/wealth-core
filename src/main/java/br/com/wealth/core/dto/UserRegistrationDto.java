package br.com.wealth.core.dto;



import br.com.wealth.core.enums.RolesTypeEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UserRegistrationDto {
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmPassword;


    private RolesTypeEnum role;
}
