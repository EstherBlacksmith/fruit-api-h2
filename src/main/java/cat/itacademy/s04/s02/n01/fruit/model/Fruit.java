package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.*;
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
    @Setter
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;


    public Fruit(String name, int weightInKilos, Provider provider) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.provider = provider;
    }

    public Fruit(FruitRequest fruitRequest, Provider provider) {
        this.name = fruitRequest.getName();
        this.weightInKilos = fruitRequest.getWeightInKilos();
        this.provider = provider;

    }

}