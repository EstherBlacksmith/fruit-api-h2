package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.exception.GlobalExceptionHandler;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.repository.FruitRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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

    //TODO: list getALlFruitrs
    //TODO: GetAFruit
    //TODO: updateAfruit
    //TODO: deleteAfruit

}
