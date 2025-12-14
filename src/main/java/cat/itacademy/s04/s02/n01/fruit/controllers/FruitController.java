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
@RequestMapping()
public class FruitController {

    private final FruitService fruitService;

    public FruitController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    @GetMapping("/fruits")
    public ResponseEntity<List<Fruit>> getFruits() {

        List<Fruit> listFruit = fruitService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listFruit);
    }

    @PostMapping("/fruits")
    public ResponseEntity<FruitResponse> createFruit(
            @Valid @RequestBody FruitRequest fruitRequest, @RequestParam String providerName) {

        FruitResponse fruitResponse = fruitService.save(fruitRequest, providerName);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fruitResponse);
    }

    @GetMapping("/fruits/{id}")
    public ResponseEntity<Fruit> getFruit(@PathVariable Long id) {

        Fruit fruitResponse = fruitService.get(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fruitResponse);
    }

    @PutMapping("/fruits/{id}")
    public ResponseEntity<FruitResponse> updateFruit(
            @PathVariable Long id,
            @Valid @RequestBody FruitRequest fruitRequest) {

        FruitResponse fruitResponse = fruitService.update(id, fruitRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fruitResponse);
    }

    @DeleteMapping("/fruits/{id}")
    public ResponseEntity<Fruit> deleteFruit(
            @PathVariable Long id) {

        Fruit fruitResponse = fruitService.get(id);
        fruitService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(fruitResponse);
    }
}
