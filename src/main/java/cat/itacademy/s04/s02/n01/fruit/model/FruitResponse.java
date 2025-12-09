package cat.itacademy.s04.s02.n01.fruit.model;

public class FruitResponse {
    private Long id;
    private String name;
    private int weightInKilos;


    public FruitResponse(Long id,String name, int weightInKilos) {
        this.name = name;
        this.weightInKilos = weightInKilos;
    }

    public String getName() {
        return name;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }

}
