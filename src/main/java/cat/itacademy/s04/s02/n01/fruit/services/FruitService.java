package cat.itacademy.s04.s02.n01.fruit.services;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
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

    public Fruit save(FruitRequest fruitRequest) {
        Fruit fruit = new Fruit(
                fruitRequest.getName(),
                fruitRequest.getWeightInKilos()
        );

        return fruitRepository.save(fruit);
    }

    //TODO: list getALlFruitrs
    //TODO: GetAFruit
    //TODO: updateAfruit
    //TODO: deleteAfruit

}
