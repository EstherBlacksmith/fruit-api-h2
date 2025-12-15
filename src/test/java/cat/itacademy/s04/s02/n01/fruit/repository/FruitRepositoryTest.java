package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
class FruitRepositoryTest {
    @Autowired
    private FruitRepository fruitRepository;
    @Autowired
    private ProviderRepository providerRepository;

    private Provider provider;

    @BeforeEach
    public void setUp(){
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");
        provider = new Provider(providerRequest);
        providerRepository.save(provider);
    }

    @Test
    @DisplayName("Test must return the details of the new fruit")
    void save_testMustPasAndReturnTheDetails() {
        Fruit fruit = new Fruit("Poma", 1, provider);
        Fruit savedFruit = fruitRepository.save(fruit);

        assertDoesNotThrow(() -> fruitRepository.save(fruit));
        Assertions.assertEquals("Poma", savedFruit.getName());
        Assertions.assertEquals(1, savedFruit.getWeightInKilos());
    }

    @Test()
    @DisplayName("Test must return error if the value is empty")
    void save_testMustReturnErrorIfTheNameIsEmpty() {
        Fruit fruit = new Fruit(null, 1, provider);

        Assertions.assertThrows(ConstraintViolationException.class, () -> fruitRepository.save(fruit));
    }

    @Test()
    @DisplayName("Test must return error if the value in kilos is 0")
    void save_testMustReturnErrorIfTheValueZero() {
        Fruit fruit = new Fruit("Pera", 0, provider);

        Assertions.assertThrows(ConstraintViolationException.class, () -> fruitRepository.save(fruit));
    }

    @Test()
    @DisplayName("Test must return empty list if there are no fruits")
    void save_testMustReturnEmptyListOfFruits() {
        Assertions.assertNotNull(fruitRepository.findAll(), "The list is not null");
    }

    @Test()
    @DisplayName("Test must return a list of fruits")
    void save_testMustReturnListOfFruits() {
        Fruit fruit1 = new Fruit("Pera", 12, provider);
        Fruit fruit2 = new Fruit("Poma", 2, provider);
        fruitRepository.save(fruit1);
        fruitRepository.save(fruit2);

        Assertions.assertNotNull(fruitRepository.findAll(), "The list is not null");
        Assertions.assertTrue(fruitRepository.findAll().contains(fruit1));
        Assertions.assertTrue(fruitRepository.findAll().contains(fruit2));
    }
}