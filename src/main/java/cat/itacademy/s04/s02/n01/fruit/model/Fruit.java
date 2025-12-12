package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Fruit {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    private String name;
    @Setter
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