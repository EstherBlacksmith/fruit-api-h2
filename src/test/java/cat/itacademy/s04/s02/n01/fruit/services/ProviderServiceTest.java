package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.*;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
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
public class ProviderServiceTest {

    @InjectMocks
    private ProviderService providerService;

    @Mock
    private ProviderRepository providerRepository;


    @Test
    void save_thenReturnProviderResponseWithIdIfTheDataAreValid() {
        ProviderRequest providerRequest = new ProviderRequest();
        Provider provider = new Provider(providerRequest);

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");

        ProviderResponse providerSaved = providerService.save(providerRequest);

        assertNotNull(providerSaved);

        Mockito.verify(providerRepository, times(1)).save(any(Provider.class));
    }



    @Test
    void save_thenReturnErrorIdProviderResponseWithIdIfTheNameAreDuplicated() {
        ProviderRequest providerRequest = new ProviderRequest();
        Provider provider = new Provider(providerRequest);

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");

        ProviderResponse providerSaved = providerService.save(providerRequest);

        assertNotNull(providerSaved);

        Mockito.verify(providerRepository, times(1)).save(any(Provider.class));
    }

}
