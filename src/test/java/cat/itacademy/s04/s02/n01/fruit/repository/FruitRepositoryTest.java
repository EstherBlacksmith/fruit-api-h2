package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.InvalidFruitRequestException;
import cat.itacademy.s04.s02.n01.fruit.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class FruitRepositoryTest {
    @Autowired
    private FruitRepository fruitRepository;


    @Test
    @DisplayName("Test must return the details of the new fruit")
    void save_testMustPasAndReturnTheDetails() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");
        Provider provider =new Provider(providerRequest);

        Fruit fruit = new Fruit("Poma", 1, provider);
        Fruit savedFruit = fruitRepository.save(fruit);

        assertDoesNotThrow(() -> fruitRepository.save(fruit));
        Assertions.assertEquals("Poma", savedFruit.getName());
        Assertions.assertEquals(1, savedFruit.getWeightInKilos());
    }

    @Test()
    @DisplayName("Test must return error if the value is empty")
    void save_testMustReturnErrorIfTheNameIsEmpty() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");
        Provider provider =new Provider(providerRequest);

        Fruit fruit = new Fruit(null, 1, provider);
        Assertions.assertThrows(ConstraintViolationException.class,() ->  fruitRepository.save(fruit));
    }



    @Test()
    @DisplayName("Test must return error if the value in kilos is 0")
    void save_testMustReturnErrorIfTheValueZero() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");
        Provider provider =new Provider(providerRequest);

        Fruit fruit = new Fruit("Pera", 0, provider);
        Assertions.assertThrows(ConstraintViolationException.class,() ->  fruitRepository.save(fruit));
    }
}