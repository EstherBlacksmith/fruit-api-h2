package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.provider.dto.ProviderResponse;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderDuplicateNameException;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import cat.itacademy.s04.s02.n01.fruit.provider.service.ProviderService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)


public class ProviderServiceTest {
    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private ProviderService providerService;

    @Test
    void save_thenReturnProviderResponseWithIdIfTheDataAreValid() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        Provider provider = new Provider(providerRequest);

        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");

        Provider.ProviderResponse providerSaved = providerService.save(providerRequest);

        assertNotNull(providerSaved);

        Mockito.verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    void save_thenReturnErrorIdProviderResponseWithIdIfTheNameAreDuplicated() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");
        when(providerRepository.save(any(Provider.class)))
                .thenThrow(ProviderDuplicateNameException.class);

        Assertions.assertThrows(ProviderDuplicateNameException.class, () -> {
            providerService.save(providerRequest);
        });

        verify(providerRepository, times(1)).save(any(Provider.class));
    }

    @Test
    void getAll_returnsList() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");
        Provider provider = new Provider(providerRequest);
        when(providerRepository.save(any(Provider.class))).thenReturn(provider);

        when(providerRepository.findAll()).thenReturn(List.of(provider));

        List<Provider> result = providerService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo("Frutas SL");
    }

    @Test
    void get_existingId_returnsProvider() {
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Frutas SL");
        providerRequest.setCountry("Spain");

        Provider provider = new Provider(providerRequest);
        providerRepository.save(provider);
        Long id = provider.getId();

        when(providerRepository.findById(provider.getId())).thenReturn(Optional.of(provider));

        Provider result = providerService.get(id);

        assertThat(result.getId()).isEqualTo(id);
    }

}
