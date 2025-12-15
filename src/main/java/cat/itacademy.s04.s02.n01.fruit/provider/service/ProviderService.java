package cat.itacademy.s04.s02.n01.fruit.provider.service;

import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderDuplicateNameException;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Validated
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    public ProviderService(@Valid ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    public Provider.ProviderResponse save(@Valid @RequestBody Provider.ProviderRequest providerRequest) {
        providerRepository.findByName(providerRequest.getName()).ifPresent(providerToFind -> {
            throw new ProviderDuplicateNameException("Provider with name '" + providerRequest.getName()
                    + "' already exists");
        });

        Provider provider = new Provider(
                providerRequest.getName(),
                providerRequest.getCountry()
        );

        Provider saved = providerRepository.save(provider);

        return new Provider.ProviderResponse(
                saved.getId(),
                saved.getName(),
                saved.getCountry()
        );
    }
}
