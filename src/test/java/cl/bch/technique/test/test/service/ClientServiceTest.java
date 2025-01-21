package cl.bch.technique.test.test.service;

import cl.bch.technique.test.test.dtos.RegisterClientDto;
import cl.bch.technique.test.test.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void whenSeekExistingClient_thenFlowAsExpected() {
        RegisterClientDto registerClientDto = RegisterClientDto.builder()
                .id(1)
                .rut("12345678-9")
                .email("a@b.cl")
                .dateBirth("1970-01-01")
                .firstName("Alan")
                .lastName("Doe")
                .mobilePhone("9533725")
                .password("1234")
                .build();

        Mockito.when(clientRepository.findById(1))
                .thenReturn(registerClientDto);

        RegisterClientDto r = clientService.getById(1);

        assertEquals(r.getRut(), registerClientDto.getRut());
    }

    @Test
    void whenSeekNonExistingClient_thenNullReturned() {

        Mockito.when(clientRepository.findById(1))
                .thenReturn(null);

        RegisterClientDto r = clientService.getById(1);

        assertEquals(r, null);
    }

}