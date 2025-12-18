package cat.itacademy.s04.s02.n01.fruit.provider.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProviderRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Country is required")
    private String country;
}

