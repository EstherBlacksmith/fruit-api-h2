package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.*;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
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


    public ProviderResponse save(@Valid @RequestBody ProviderRequest providerRequest) {
        providerRepository.findByName(providerRequest.getName()).ifPresent(p -> {
            throw new IllegalArgumentException("Provider with name '" + providerRequest.getName() + "' already exists");
        });
        Provider provider = new Provider(
                providerRequest.getName(),
                providerRequest.getCountry()
        );

        Provider saved = providerRepository.save(provider);

        return new ProviderResponse(
                saved.getId(),
                saved.getName(),
                saved.getCountry()
        );
    }
}
