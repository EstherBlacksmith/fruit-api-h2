package cat.itacademy.s04.s02.n01.fruit.model;

import lombok.Getter;

public class FruitResponse {
    @Getter
    private Long id;
    @Getter
    private String name;
    @Getter
    private int weightInKilos;


    public FruitResponse(Long id, String name, int weightInKilos) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.id = id;
    }

}
