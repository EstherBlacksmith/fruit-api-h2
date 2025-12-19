package cat.itacademy.s04.s02.n01.fruit.fruit.controller;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.fruit.service.FruitService;
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
    public ResponseEntity<List<Fruit>> getFruits(
            @RequestParam(name = "providerId", required = false) Long id) {

        List<Fruit> listFruits;
        if (id != null) {
            listFruits = fruitService.getAllFruitsByProviderId(id);
        } else {
            listFruits = fruitService.getAll();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listFruits);
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
