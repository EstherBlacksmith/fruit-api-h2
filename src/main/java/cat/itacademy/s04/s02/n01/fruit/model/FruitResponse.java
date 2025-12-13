package cat.itacademy.s04.s02.n01.fruit.model;

import lombok.Getter;

public class FruitResponse {
    @Getter
    private Long id;
    @Getter
    private String name;
    @Getter
    private int weightInKilos;
    @Getter
    private Provider provider;

    public FruitResponse(Long id, String name, int weightInKilos, Provider provider) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.provider = provider;
        this.id = id;
    }

}
