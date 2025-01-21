package cl.bch.technique.test.test.service;

import cl.bch.technique.test.test.dtos.LoginClientDto;
import cl.bch.technique.test.test.dtos.RegisterClientDto;
import cl.bch.technique.test.test.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

;

@Service
public class ClientService {

    private final Logger logger = Logger.getLogger(ClientService.class.getName());

    //@Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<RegisterClientDto> getAll() {
        return clientRepository.findAll();
    }

    public RegisterClientDto getById(int id) {
        logger.info("getById called");
        return clientRepository.findById(id);
    }

    public RegisterClientDto getByRut(String rut) {

        return clientRepository.findByRut(rut);
    }

    public RegisterClientDto signup(RegisterClientDto input) {
        return input;
    }

    public RegisterClientDto authenticate(LoginClientDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getRut(),
                        input.getPassword()
                )
        );

        return getByRut(input.getRut());
    }
}
