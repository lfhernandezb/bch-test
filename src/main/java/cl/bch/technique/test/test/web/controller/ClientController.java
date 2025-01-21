package cl.bch.technique.test.test.web.controller;

import cl.bch.technique.test.test.dtos.LoginClientDto;
import cl.bch.technique.test.test.dtos.RegisterClientDto;
import cl.bch.technique.test.test.exception.ClientNotFoundException;
import cl.bch.technique.test.test.exception.InvalidNextStepException;
import cl.bch.technique.test.test.exception.LockedClientException;
import cl.bch.technique.test.test.responses.LoginResponse;
import cl.bch.technique.test.test.service.ClientService;
import cl.bch.technique.test.test.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/cliente")
@RestController
public class ClientController {

    final int MAX_ATTEMPTS_PERMITED = 3;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private record UserExtraInfo(int numeroIntento, boolean bloqueo, String siguienteEtapa) {}

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<UserDetails> authenticatedUser() {
        logger.info("authenticatedUser invoked");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(userDetails);
    }

    @GetMapping("/consulta/{id}")
    public ResponseEntity<RegisterClientDto> getById(@PathVariable int id) {
        logger.info("getById invoked");

        Map<String, Object> map = new HashMap<>();

        // obtengo el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // obtengo el token
        RegisterClientDto authenticatedClient = clientService.getByRut(userDetails.getUsername());

        String token = authenticatedClient.getToken();

        // obtengo los claims
        Claims claims; // = jwtService.extractAllClaims(token);

        try {
            claims = jwtService.extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }

        String siguienteEtapa = (String) claims.get("siguiente_etapa");

        if (!siguienteEtapa.equals("consulta_cliente")) {
            throw new InvalidNextStepException("La siguiente etapa no es la adecuada");
        }

        RegisterClientDto registerClientDto = clientService.getById(id);

        // seteamos siguiente etapa en el token
        map.putIfAbsent("nroIntento", claims.get("numeroIntento"));

        map.putIfAbsent("bloqueo",  claims.get("bloqueo"));

        map.putIfAbsent("siguiente_etapa", "guardar_cliente");

        authenticatedClient.setToken(jwtService.generateToken(map, authenticatedClient));

        if (registerClientDto == null) {
            throw new ClientNotFoundException("No se encontró el cliente con el id proporcionado");
        }

        return ResponseEntity.ok(registerClientDto);

    }

    @GetMapping("/all")
    public ResponseEntity<List<RegisterClientDto>> allUsers() {
        logger.info("allUsers invoked");
        List <RegisterClientDto> users = clientService.getAll();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/guardar")
    public Object register(@Valid @RequestBody RegisterClientDto registerUserDto) {

        // obtengo el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // obtengo el token
        RegisterClientDto authenticatedClient = clientService.getByRut(userDetails.getUsername());

        String token = authenticatedClient.getToken();

        // obtengo los claims
        Claims claims; // = jwtService.extractAllClaims(token);

        try {
            claims = jwtService.extractAllClaims(token);
        } catch (ExpiredJwtException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            authenticatedClient.setToken(null);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }


        String siguienteEtapa = (String) claims.get("siguiente_etapa");

        if (!siguienteEtapa.equals("guardar_cliente")) {
            throw new InvalidNextStepException("La siguiente etapa no es la adecuada");
        }

        RegisterClientDto registerUserDto1 = clientService.signup(registerUserDto);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginClientDto loginUserDto) {

        Map<String, Object> map = new HashMap<>();

        Claims claims;

        RegisterClientDto authenticatedUser = null;

        UserExtraInfo userExtraInfo; // = new UserExtraInfo();

        RegisterClientDto registerClientDto = clientService.getByRut(loginUserDto.getRut());

        String token = registerClientDto.getToken();

        if (token != null) {
            try {
                claims = jwtService.extractAllClaims(token);
            } catch (ExpiredJwtException e) {
                registerClientDto.setToken(null);
                throw new RuntimeException(e);
            } catch (UnsupportedJwtException e) {
                throw new RuntimeException(e);
            } catch (MalformedJwtException e) {
                registerClientDto.setToken(null);
                throw new RuntimeException(e);
            } catch (SignatureException e) {
                registerClientDto.setToken(null);
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }

            System.out.println(claims);
            userExtraInfo = new UserExtraInfo((Integer) claims.get("nroIntento"), (Boolean) claims.get("bloqueo"), (String) claims.get("siguiente_etapa"));

            if (userExtraInfo.bloqueo) {
                throw new LockedClientException("El usuario está bloqueado por máximo número de intentos fallidos");
            }
        } else {
            userExtraInfo = new UserExtraInfo(0, false, "consulta_cliente");
        }

        try {
            authenticatedUser = clientService.authenticate(loginUserDto);
        } catch (BadCredentialsException ex) {
            boolean bloqueo;
            map.putIfAbsent("nroIntento", userExtraInfo.numeroIntento + 1);

            if (userExtraInfo.numeroIntento + 1 >= MAX_ATTEMPTS_PERMITED) {
                bloqueo =  true;
            } else {
                bloqueo = false;
            }

            map.putIfAbsent("bloqueo", Boolean.valueOf(bloqueo));

            map.putIfAbsent("siguiente_etapa", "consulta_cliente");

            registerClientDto.setToken(jwtService.generateToken(map, registerClientDto));

            throw new BadCredentialsException("");
        }

        map.putIfAbsent("nroIntento", 0);

        map.putIfAbsent("bloqueo", false);

        map.putIfAbsent("siguiente_etapa", "consulta_cliente");

        registerClientDto.setToken(jwtService.generateToken(map, registerClientDto));

        String jwtToken = jwtService.generateToken(map, authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();//new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

}