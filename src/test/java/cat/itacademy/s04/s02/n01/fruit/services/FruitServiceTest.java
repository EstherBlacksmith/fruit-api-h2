package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.fruit.service.FruitService;
import cat.itacademy.s04.s02.n01.fruit.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;

import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

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

    @Mock
    private ProviderRepository providerRepository;

    @Test
    @DisplayName("Should return a fruit when test is passed")
    void save_testIsPassedReturnsAFruit() {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);
        fruitRequest.setProviderName("Las Frutas");

        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");

        Provider provider = new Provider(providerRequest);
        Fruit fruit1 = new Fruit(fruitRequest,provider);

        when(providerRepository.findByName("Las Frutas")).thenReturn(Optional.of(provider));

        when(fruitRepository.save(any(Fruit.class))).thenReturn(fruit1);

        FruitResponse savedFruit = fruitService.save(fruitRequest,provider.getName());

        Assertions.assertNotNull(savedFruit);

        Mockito.verify(fruitRepository, times(1)).save(any(Fruit.class));
    }
}

