package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FruitRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Kilos must be at least 1")
    private int weightInKilos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeightInKilos(int weightInKilos) {
        this.weightInKilos = weightInKilos;
    }

    public int getWeightInKilos() {
        return weightInKilos;
    }
}
