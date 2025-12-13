package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.*;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    public ProviderService(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }


    public ProviderResponse save(ProviderRequest providerRequest) {
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
