package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping("/fruits")
    public Fruit postFruit(@Valid @RequestBody FruitRequest fruitRequest) {
        return fruitService.save(fruitRequest);
    }


    //TODO: PUT	/fruits/{id}	Actualitzar fruita
    //TODO: DELETE	/fruits/{id}	Eliminar per id
    //TODO: GET	/fruits/{id}	Obtenir una fruita per id
    //TODO: GET	/fruits	Obtenir totes les fruites
}
