package cat.itacademy.s04.s02.n01.fruit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Provider {
    @Id
    @GeneratedValue
    private Long id;
    @Setter
    @NotBlank(message = "Name is required")
    private String name;
    @Setter
    @NotBlank(message = "Country is required")
    private String country;

    public Provider(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public Provider(ProviderRequest providerRequest) {
        this.name = providerRequest.getName();
        this.country = providerRequest.getCountry();
    }
}