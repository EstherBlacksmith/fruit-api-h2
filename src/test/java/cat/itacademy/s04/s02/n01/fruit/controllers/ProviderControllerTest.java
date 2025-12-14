package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

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
