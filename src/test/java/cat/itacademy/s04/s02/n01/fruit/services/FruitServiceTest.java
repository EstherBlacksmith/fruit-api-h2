package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;

    @InjectMocks
    private FruitService fruitService;

    @Test
    @DisplayName("Should return a fruit when test is passed")
    void save_testIsPassedReturnsAFruit() {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        Fruit fruit1 = new Fruit(fruitRequest);

        when(fruitRepository.save(any(Fruit.class))).thenReturn(fruit1);

        FruitResponse savedFruit = fruitService.save(fruitRequest);

        assertNotNull(savedFruit);

        Mockito.verify(fruitRepository, times(1)).save(any(Fruit.class));
    }
}
