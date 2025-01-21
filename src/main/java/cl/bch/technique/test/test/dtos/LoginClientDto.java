package cl.bch.technique.test.test.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginClientDto {
    @NotEmpty(message = "Se requiere el rut.")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9]$", flags = { Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE }, message = "El largo del rut debe estar entre 9 y 10 caractered y debe contener guión.")
    private String rut;
    @NotEmpty(message = "Se requiere la contraseña.")
    @Size(min = 4, max = 8, message = "El largo de la contraseña debe estar entre 4 y 8 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$", flags = { Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE }, message = "La contraseña debe tener un largo de por lo mnenos 4 caracteres alfanuméricos.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
