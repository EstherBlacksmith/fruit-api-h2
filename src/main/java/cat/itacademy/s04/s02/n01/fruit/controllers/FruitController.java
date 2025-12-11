package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/{id}")
    public ResponseEntity<Fruit> getFruit(@PathVariable Long id) {

        Fruit fruitResponse = fruitService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fruitResponse);


    }

    @GetMapping("/")
    public ResponseEntity<List<Fruit>>  getFruits() {

        List<Fruit> listFruit= fruitService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listFruit);
    }

    //TODO: PUT	/fruits/{id}	Actualitzar fruita
    //TODO: DELETE	/fruits/{id}	Eliminar per id


}
