package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Fruit {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Kilos are required")
    @Min(value = 1, message = "Kilos must be at least 1")
    private int weightInKilos;

    public Fruit(String name, int weightInKilos) {
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }

}