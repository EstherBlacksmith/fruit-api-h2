package cat.itacademy.s04.s02.n01.fruit.provider.service;

import cat.itacademy.s04.s02.n01.fruit.provider.dto.ProviderRequest;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.ProviderResponse;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderDuplicateNameException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderHasFruitsAssociatedException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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

    public List<Provider> getAll() {
        return providerRepository.findAll();
    }

    public ProviderResponse update(Long id, @Valid ProviderRequest providerRequest) {
        Provider provider = providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Provider doesn't exists"));

        providerRepository.findByName(providerRequest.getName()).ifPresent(providerToFind -> {
            throw new ProviderDuplicateNameException("Provider with name '" + providerRequest.getName()
                    + "' already exists");
        });

        provider.setName(providerRequest.getName());
        provider.setCountry(providerRequest.getCountry());

        Provider updated = providerRepository.saveAndFlush(provider);

        return new ProviderResponse(
                updated.getId(),
                updated.getName(),
                updated.getCountry()
        );

    }

    public Provider get(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("Provider doesn't exists"));
    }
    @Transactional
    public HttpStatus delete(Long id) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException("Provider doesn't exists"));

        try {
            providerRepository.delete(provider);
            providerRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ProviderHasFruitsAssociatedException("Cannot delete provider due to existing fruits");
        }
        return HttpStatus.NO_CONTENT;
    }
}