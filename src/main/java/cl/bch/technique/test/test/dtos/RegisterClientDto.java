package cl.bch.technique.test.test.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RegisterClientDto implements UserDetails {

    //@Value("${validation.password}")
    //private String passwordPattern;
    // @JsonIgnoreProperties({"token", "password"})
    private int id;
    // * @NotEmpty(message = "Se requiere el nombre.")
    // * @Size(min = 2, max = 64, message = "El largo del nombre debe estar entre 2 y 64 caracteress.")
    @NotEmpty(message = "Se requiere el rut.")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9]$", flags = { Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE }, message = "El largo del rut debe estar entre 9 y 10 caractered y debe contener guión.")
    private String rut;
    @NotEmpty(message = "Se requiere el nombre.")
    @Size(min = 2, max = 64, message = "El largo del nombre debe estar entre 2 y 64 caracteres.")
    @JsonProperty("first_name")
    private String firstName;
    @NotEmpty(message = "Se requiere el apellido.")
    @Size(min = 2, max = 64, message = "The length of name must be between 2 and 64 characters.")
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("date_birth")
    private String dateBirth;
    @NotEmpty(message = "Se requiere el teléfono móvil.")
    @Pattern(regexp = "^[0-9]{9}$", flags = { Pattern.Flag.CASE_INSENSITIVE }, message = "El largo del teléfono móvil debe ser 9 dígitos.")
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    @NotEmpty(message = "Se requiere el email.")
    @Email(message = "El email no es valido.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;
    private String address;
    @JsonProperty("city_id")
    private String cityId;
    @JsonProperty("session_active")
    private boolean sessionActive;
    @NotEmpty(message = "Se requiere la contraseña.")
    @Size(min = 4, max = 8, message = "El largo de la contraseña debe estar entre 4 y 8 caracteres.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,}$", flags = { Pattern.Flag.CASE_INSENSITIVE, Pattern.Flag.MULTILINE }, message = "La contraseña debe tener un largo de por lo mnenos 4 caracteres alfanuméricos.")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return rut;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
