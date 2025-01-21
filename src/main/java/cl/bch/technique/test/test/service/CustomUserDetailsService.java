package cl.bch.technique.test.test.service;

import cl.bch.technique.test.test.dtos.RegisterClientDto;
import cl.bch.technique.test.test.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        RegisterClientDto registerClientDto = clientService.getByRut(username);

        if (registerClientDto == null) {
            throw new UsernameNotFoundException("Usuario no encontrado por rut: " + username);
        }

        // si la contraseña no está encriptada, la encripto
        Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

        if (!BCRYPT_PATTERN.matcher(registerClientDto.getPassword()).matches()) {
            // no encriptado, encripto
            registerClientDto.setPassword(passwordEncoder.encode(registerClientDto.getPassword()));
        }

        return registerClientDto;
    }
}
