package cl.bch.technique.test.test.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class RegisterClientDtoTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenDataIsValid_thenFlowAsExpected() {
        RegisterClientDto registerClientDto = RegisterClientDto.builder()
                .id(1)
                .rut("12345678-9")
                .email("a@b.cl")
                .dateBirth("1970-01-01")
                .firstName("Alan")
                .lastName("Doe")
                .mobilePhone("953372570")
                .password("1234")
                .build();

        Set<ConstraintViolation<RegisterClientDto>> violations = validator.validate(registerClientDto);

        System.out.println(violations);

        assertEquals((long) violations.size(), 0);

    }

    @Test
    void whenInvalidRutLength_thenError() {
        RegisterClientDto registerClientDto = RegisterClientDto.builder()
                .id(1)
                .rut("12")
                .email("a@b.cl")
                .dateBirth("1970-01-01")
                .firstName("Alan")
                .lastName("Doe")
                .mobilePhone("953372570")
                .password("1234")
                .build();

        Set<ConstraintViolation<RegisterClientDto>> violations = validator.validate(registerClientDto);

        System.out.println(violations);

        assertEquals((long) violations.size(), 1);
        assertEquals(violations.stream().toList().get(0).getMessage(), "El largo del rut debe estar entre 9 y 10 caractered y debe contener guión.");

    }

    @Test
    void whenInvalidMobileNumber_thenError() {
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

        Set<ConstraintViolation<RegisterClientDto>> violations = validator.validate(registerClientDto);

        System.out.println(violations);

        assertEquals((long) violations.size(), 1);
        assertEquals(violations.stream().toList().get(0).getMessage(), "El largo del teléfono móvil debe ser 9 dígitos.");

    }

    @Test
    void whenInvalidEmail_thenError() {
        RegisterClientDto registerClientDto = RegisterClientDto.builder()
                .id(1)
                .rut("12345678-9")
                .email("a@")
                .dateBirth("1970-01-01")
                .firstName("Alan")
                .lastName("Doe")
                .mobilePhone("953372570")
                .password("1234")
                .build();

        Set<ConstraintViolation<RegisterClientDto>> violations = validator.validate(registerClientDto);

        System.out.println(violations);

        assertEquals((long) violations.size(), 1);
        assertEquals(violations.stream().toList().get(0).getMessage(), "El email no es valido.");

    }

}