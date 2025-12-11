package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DataJpaTest
class FruitRepositoryTest {
    @Autowired
    private FruitRepository fruitRepository;


    @Test
    @DisplayName("Test must return the details of the new fruit")
    void save_testMustPasAndReturnTheDetails(){
        Fruit fruit = new Fruit("Poma",1);
        Fruit savedFruit  = fruitRepository.save(fruit);
        assertDoesNotThrow(() -> fruitRepository.save(fruit));
        Assertions.assertEquals("Poma", savedFruit.getName());
        Assertions.assertEquals(1, savedFruit.getWeightInKilos());
    }

}