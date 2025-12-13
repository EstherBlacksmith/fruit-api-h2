package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Country is required")
    private String country;

}
