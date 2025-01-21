package cl.bch.technique.test.test.web.controller;

import cl.bch.technique.test.test.dtos.RegisterClientDto;
import cl.bch.technique.test.test.service.ClientService;
import cl.bch.technique.test.test.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Autowired
    JwtService jwtService;
    RegisterClientDto client;
    String tokenOk, tokenNoOk;

    @MockBean
    private ClientService clientService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        // jwtService = new JwtService();

        Map<String, Object> map = new HashMap<>();

        map.putIfAbsent("nroIntento", 1);

        map.putIfAbsent("bloqueo", false);

        map.putIfAbsent("siguiente_etapa", "otra_consulta");

        client = RegisterClientDto
                .builder()
                .id(1)
                .email("a1@b.cl")
                .rut("11111111-1")
                .firstName("John")
                .lastName("Smith")
                .mobilePhone("123456789")
                .password("1234")
                .build();

        tokenNoOk = jwtService.generateToken(map, client);

        map.put("siguiente_etapa", "consulta_cliente");

        tokenOk = jwtService.generateToken(map, client);

    }

    @Test
    void whenNotCorrectStep_thenError() throws Exception {
        client.setToken(tokenNoOk);

        Mockito.when(clientService.getByRut(Mockito.anyString()))
                .thenReturn(client);

        Mockito.when(clientService.getById(Mockito.anyInt()))
                .thenReturn(client);

        mvc.perform(get("/cliente/consulta/1")
                        .header("Authorization", "Bearer " + tokenNoOk)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    void whenCorrectStep_thenFlowAsExpected() throws Exception {
        client.setToken(tokenOk);

        Mockito.when(clientService.getByRut(Mockito.anyString()))
                .thenReturn(client);

        Mockito.when(clientService.getById(Mockito.anyInt()))
                .thenReturn(client);

        mvc.perform(get("/cliente/consulta/1")
                        .header("Authorization", "Bearer " + tokenOk)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}