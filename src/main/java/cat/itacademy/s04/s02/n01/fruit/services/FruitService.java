package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FruitService {
    @Autowired
    private FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    public FruitResponse save(FruitRequest fruitRequest) {
        Fruit fruit = new Fruit(
                fruitRequest.getName(),
                fruitRequest.getWeightInKilos()
        );

        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponse(
                saved.getId(),
                saved.getName(),
                saved.getWeightInKilos()
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

        FruitResponse fruitResponse = new FruitResponse(fruitRepository.findById(id));
        Fruit fruit1 = fruitRepository.findBy(fruitResponse.getId())
        Fruit saved = fruitRepository.saveAndFlush(fruit);

        return new FruitResponse(
                saved.getId(),
                saved.getName(),
                saved.getWeightInKilos()
        );

    }

    //TODO: deleteAfruit

}
