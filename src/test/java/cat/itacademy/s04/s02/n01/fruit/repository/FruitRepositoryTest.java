package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.FruitNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;

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
    void findAll_testMustReturnEmptyListOfFruits() {
        Assertions.assertNotNull(fruitRepository.findAll(), "The list is not null");
    }

    @Test()
    @DisplayName("Test must return a list of fruits")
    void findAll_testMustReturnListOfFruits() {
        Fruit fruit1 = new Fruit("Pera", 12, provider);
        Fruit fruit2 = new Fruit("Poma", 2, provider);
        fruitRepository.save(fruit1);
        fruitRepository.save(fruit2);

        Assertions.assertNotNull(fruitRepository.findAll(), "The list is not null");
        assertTrue(fruitRepository.findAll().contains(fruit1));
        assertTrue(fruitRepository.findAll().contains(fruit2));
    }


    @Test()
    @DisplayName("Test must return a empty if the given id doesn't exists")
    void findById_testMustReturnEmptyIfTheIdDoesNotExists() {
        assertTrue(fruitRepository.findById(4L).isEmpty());
    }

    @Test()
    @DisplayName("Test must return a the details of the fruit")
    void findById_testMustReturnTheDetailsOfTheFruit() {
        Fruit fruit1 = new Fruit("Pera", 12, provider);

        fruitRepository.save(fruit1);

        assertTrue(fruitRepository.findById(fruit1.getId()).stream()
                .anyMatch(fruit -> fruit.getName().equals("Pera")));
    }

    @Test()
    @DisplayName("Test must return a the details of the fruit")
    void update_testMustReturnTheUpdatedFruit() {
        Fruit fruit1 = new Fruit("Pera", 12, provider);
        fruitRepository.save(fruit1);
        fruit1.setName("Poma");
        fruit1.setWeightInKilos(2);
        fruitRepository.saveAndFlush(fruit1);

        assertTrue(fruitRepository.findById(fruit1.getId()).stream()
                .anyMatch(fruit -> fruit.getName().equals("Poma")));
    }

}
/*If the data is valid, the system returns HTTP 200 OK with the updated fruit.

If the ID does not exist, it returns HTTP 404 Not Found.

If the data is invalid, it returns HTTP 400 Bad Request.*/