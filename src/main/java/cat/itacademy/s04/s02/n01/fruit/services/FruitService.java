package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.ProviderNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.model.*;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import cat.itacademy.s04.s02.n01.fruit.repository.ProviderRepository;
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

    public FruitResponse save(FruitRequest fruitRequest,String providerName) {
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
                .orElseThrow();
    }

    public List<Fruit> getAll() {
        return fruitRepository.findAll();
    }

    public FruitResponse update(Long id, @Valid FruitRequest fruitRequest) {
        Fruit fruit = fruitRepository.findById(id).orElseThrow();

        fruit.setWeightInKilos(fruitRequest.getWeightInKilos());
        fruit.setName(fruitRequest.getName());

        Provider provider = providerRepository.findByName(fruitRequest.getProviderName())
                .orElseThrow();

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
        Fruit fruit = fruitRepository.findById(id).orElseThrow();
        fruitRepository.delete(fruit);   
        return HttpStatus.NO_CONTENT;
        
    }

}
