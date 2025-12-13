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

    @NotBlank(message = "Provider is required")
    private Provider provider;
}
