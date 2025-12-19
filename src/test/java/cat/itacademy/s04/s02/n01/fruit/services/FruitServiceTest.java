package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.fruit.service.FruitService;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FruitServiceTest {

    @Mock
    private FruitRepository fruitRepository;
    @Mock
    private ProviderRepository providerRepository;
    @InjectMocks
    private FruitService fruitService;

    private FruitRequest fruitRequest;
    private Provider provider;
    private Fruit fruit;

    @BeforeEach
    void setUp() {
        provider = new Provider();
        provider.setName("Perico");

        fruitRequest = new FruitRequest();
        fruitRequest.setName("Aguacate");
        fruitRequest.setWeightInKilos(25);
        fruitRequest.setProviderName("Perico");


        fruit = new Fruit();
        fruit.setName("Aguacate");
        fruit.setWeightInKilos(25);
        fruit.setProvider(provider);
    }

    @Test
    @DisplayName("Should return a fruit when test is passed")
    void saveTestIsPassedReturnsAFruit() {
        Fruit fruit1 = new Fruit(fruitRequest, provider);

        when(providerRepository.findByName("Perico")).thenReturn(Optional.of(provider));
        when(fruitRepository.save(any(Fruit.class))).thenReturn(fruit1);

        FruitResponse savedFruit = fruitService.save(fruitRequest, provider.getName());

        assertNotNull(savedFruit);

        verify(fruitRepository, times(1)).save(any(Fruit.class));
    }

    @Test
    void saveTestReturnProviderNotFoundThrowsException() {
        when(providerRepository.findByName("Perico")).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class, () -> fruitService.save(fruitRequest, "Perico"));
        verify(fruitRepository, never()).save(any());
    }

    @Test
    void getTestReturnNotFoundThrowsException() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.get(1L));
    }

    @Test
    void updateTestReturnSuccess() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));
        when(providerRepository.findByName("Perico")).thenReturn(Optional.of(provider));
        when(fruitRepository.saveAndFlush(any(Fruit.class))).thenReturn(fruit);

        FruitResponse response = fruitService.update(1L, fruitRequest);

        assertNotNull(response);
        assertEquals("Aguacate", response.getName());
        verify(fruitRepository, times(1)).saveAndFlush(any(Fruit.class));
    }


    @Test
    void updateTestReturnFruitNotFoundException() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.update(1L, fruitRequest));
    }


    @Test
    void updateTestReturnProviderNotFoundException() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));
        when(providerRepository.findByName("ACME")).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class, () -> fruitService.update(1L, fruitRequest));
    }

    @Test
    void deleteTestReturnSuccess() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.of(fruit));

        Object status = fruitService.delete(1L);

        assertNotNull(status);
        verify(fruitRepository, times(1)).delete(fruit);
        verify(fruitRepository, times(1)).flush();
    }

    @Test
    void deleteTestReturnFruitNotFoundException() {
        when(fruitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(FruitNotFoundException.class, () -> fruitService.delete(1L));
    }

    @Test
    void getAllTestReturnsFruitList() {
        when(fruitRepository.findAll()).thenReturn(List.of(fruit));

        List<Fruit> result = fruitService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getAllFruitsByProviderIdReturnSuccess() {
        when(providerRepository.findById(1L)).thenReturn(Optional.of(provider));
        when(fruitRepository.findByProvider_Id(1L)).thenReturn(List.of(fruit));

        List<Fruit> result = fruitService.getAllFruitsByProviderId(1L);

        assertEquals(1, result.size());
        verify(fruitRepository, times(1)).findByProvider_Id(1L);
    }

    @Test
    void getAllFruitsByProviderIdReturnProviderNotFoundException() {
        when(providerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProviderNotFoundException.class, () -> fruitService.getAllFruitsByProviderId(1L));
        verify(fruitRepository, never()).findByProvider_Id(anyLong());
    }
}

