package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Fruit {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
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