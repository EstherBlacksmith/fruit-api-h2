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
    private String providerName;

    public FruitResponse(Long id, String name, int weightInKilos, String providerName) {
        this.name = name;
        this.weightInKilos = weightInKilos;
        this.providerName = providerName;
        this.id = id;
    }

}
