package cl.bch.technique.test.test.repository;

import cl.bch.technique.test.test.dtos.RegisterClientDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClientRepository {

    @Getter
    List<RegisterClientDto> list;
    public ClientRepository() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext();

        // Through Classpath
        Resource resource = applicationContext
                .getResource("classpath:/clients.json");


        // create Object Mapper
        ObjectMapper mapper = new ObjectMapper();

        // read JSON file and map/convert to java POJO
        try {
            //ClassPathResource resource = new ClassPathResource("clients.json");
            RegisterClientDto[] rArray = mapper.readValue(resource.getInputStream(), RegisterClientDto[].class);

            list = Arrays.stream(rArray).toList();

            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }


        /*
        list = new ArrayList<>();

        list.add(
                RegisterClientDto.builder()
                        .id(1)
                        .rut("12345678-9")
                        .email("cliente1@b.cl")
                        .dateBirth("1970-01-01")
                        .firstName("Alan")
                        .lastName("Doe")
                        .mobilePhone("123456789")
                        .password("1234")
                        .build()
        );

        list.add(
                RegisterClientDto.builder()
                        .id(1)
                        .rut("23456789-0")
                        .email("cliente2@b.cl")
                        .dateBirth("1970-01-01")
                        .firstName("Bob")
                        .lastName("Doe")
                        .mobilePhone("2345678901")
                        .password("2345")
                        .build()
        );

        list.add(
                RegisterClientDto.builder()
                        .id(1)
                        .rut("34567890-1")
                        .email("cliente3@b.cl")
                        .dateBirth("1970-01-01")
                        .firstName("Claire")
                        .lastName("Doe")
                        .mobilePhone("3456789012")
                        .password("3456")
                        .build()
        );

        list.add(
                RegisterClientDto.builder()
                        .id(1)
                        .rut("45678901-2")
                        .email("cliente4@b.cl")
                        .dateBirth("1970-01-01")
                        .firstName("Daisy")
                        .lastName("Doe")
                        .mobilePhone("4567890123")
                        .password("4567")
                        .build()
        );

        list.add(
                RegisterClientDto.builder()
                        .id(1)
                        .rut("56789012-3")
                        .email("cliente5@b.cl")
                        .dateBirth("1970-01-01")
                        .firstName("Emily")
                        .lastName("Doe")
                        .mobilePhone("5678901234")
                        .password("5678")
                        .build()
        );

         */

    }

    public RegisterClientDto findById(int id) {
        return list.stream().filter(client -> client.getId() == id).findFirst().orElse(null);
    }

    public RegisterClientDto findByRut(String rut) {
        return list.stream().filter(client -> client.getRut().equals(rut)).findFirst().orElse(null);
    }

    public List<RegisterClientDto> findAll() {
        return list;
    }
}
