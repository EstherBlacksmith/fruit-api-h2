package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class FruitController {
    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

//TODO: POST	/fruits	Crear fruita

    @PostMapping("/fruits")
    public Fruit postFruit (@RequestBody String name, int kilos){

        return fruitService.save(fruitRequest);
    }
    //TODO: PUT	/fruits/{id}	Actualitzar fruita
    //TODO: DELETE	/fruits/{id}	Eliminar per id
    //TODO: GET	/fruits/{id}	Obtenir una fruita per id
    //TODO: GET	/fruits	Obtenir totes les fruites
}
