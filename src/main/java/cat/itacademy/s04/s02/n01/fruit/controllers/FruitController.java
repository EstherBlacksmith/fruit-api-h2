package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fruits")
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @PostMapping
    public ResponseEntity<FruitResponse> createFruit(
            @Valid @RequestBody FruitRequest fruitRequest) {

        FruitResponse fruitResponse = fruitService.save(fruitRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fruitResponse);
    }
/*
    @GetMapping("/{id}")
    public Fruit getUserFruit(@PathVariable Long id) {
    Fruit fruit = fruitService.get(id);
        if(fruit==null){
            throw new FruitNotFoundException("Fruit doesn't exists");
        }

        return fruit;
    }*/


    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruit(@PathVariable Long id) {

        Fruit fruitResponse = fruitService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fruitResponse);


    }
    //TODO: GET	/fruits/{id}	Obtenir una fruita per id


    //TODO: PUT	/fruits/{id}	Actualitzar fruita
    //TODO: DELETE	/fruits/{id}	Eliminar per id

    //TODO: GET	/fruits	Obtenir totes les fruites
}
