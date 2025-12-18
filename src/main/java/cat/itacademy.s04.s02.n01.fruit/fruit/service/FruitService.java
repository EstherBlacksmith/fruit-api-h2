package cat.itacademy.s04.s02.n01.fruit.fruit.service;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@Service
public class FruitService {
    @Autowired
    private FruitRepository fruitRepository;
    @Autowired
    private ProviderRepository providerRepository;

    public FruitService(FruitRepository fruitRepository, ProviderRepository providerRepository) {
        this.fruitRepository = fruitRepository;
        this.providerRepository = providerRepository;
    }

    public FruitResponse save(FruitRequest fruitRequest, String providerName) {
        Provider provider = providerRepository.findByName(providerName).orElseThrow(() -> new ProviderNotFoundException("Provider not found: " + providerName));

        Fruit fruit = new Fruit(
                fruitRequest.getName(),
                fruitRequest.getWeightInKilos(),
                provider
        );

        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponse(
                saved.getId(),
                saved.getName(),
                saved.getWeightInKilos(),
                saved.getProvider().getName()

        );
    }

    public Fruit get(Long id) {
        return fruitRepository.findById(id)
                .orElseThrow(() -> new FruitNotFoundException("Fruit doesn't exists"));
    }

    public List<Fruit> getAll() {
        return fruitRepository.findAll();
    }

    public FruitResponse update(Long id, @Valid FruitRequest fruitRequest) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException("Fruit doesn't exists"));

        fruit.setWeightInKilos(fruitRequest.getWeightInKilos());
        fruit.setName(fruitRequest.getName());

        Provider provider = providerRepository.findByName(fruitRequest.getProviderName())
                .orElseThrow(() -> new ProviderNotFoundException("Provider not found: " + fruitRequest.getProviderName()));

        fruit.setProvider(provider);

        Fruit updated = fruitRepository.saveAndFlush(fruit);

        return new FruitResponse(
                updated.getId(),
                updated.getName(),
                updated.getWeightInKilos(),
                updated.getProvider().getName()
        );

    }

    public Enum<HttpStatus> delete(Long id) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow(() -> new FruitNotFoundException("Fruit doesn't exists"));
        fruitRepository.delete(fruit);
        return HttpStatus.NO_CONTENT;

    }

    public List<Fruit> getAllFruitsByProviderId(Long id) {
        providerRepository.findById(id).orElseThrow(() -> new ProviderNotFoundException("Provider doesn't exists"));
        return fruitRepository.findByProvider_Id(id);
    }
}
