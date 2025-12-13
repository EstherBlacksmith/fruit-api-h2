package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.Provider;
import cat.itacademy.s04.s02.n01.fruit.model.ProviderRequest;
import cat.itacademy.s04.s02.n01.fruit.model.ProviderResponse;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import cat.itacademy.s04.s02.n01.fruit.services.ProviderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProviderControllerTest {
    @Autowired
    private ProviderRepository providerRepository;

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Test
    void createProvider_MustReturnErrorIfTheNameIsNotGiven() throws Exception {
        /*ProviderRequest providerRequest = new ProviderRequest() ;

        providerRequest.setName(null);
        providerRequest.setCountry("Spain");

        ProviderService providerService = new ProviderService(providerRepository);


        ProviderResponse providerResponse = providerService.save(providerRequest);

        assertThrows(DataIntegrityViolationException.class, () -> {
            providerRepository.saveAndFlush(providerResponse);
        }, "S'esperava una excepció de MySQL perquè el camp 'nom' és NOT NULL.");*/
    }

    }
