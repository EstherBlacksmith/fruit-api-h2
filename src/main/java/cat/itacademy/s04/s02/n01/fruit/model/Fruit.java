package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Fruit {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @NotBlank(message = "Name is required")
    private String name;
    @Setter
    @Positive(message = "Kilos must be at least 1")
    private int weightInKilos;

    public Fruit(String name, int weightInKilos) {
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public Fruit(FruitRequest fruitRequest) {
        this.name = fruitRequest.getName();
        this.weightInKilos = fruitRequest.getWeightInKilos();
    }

}