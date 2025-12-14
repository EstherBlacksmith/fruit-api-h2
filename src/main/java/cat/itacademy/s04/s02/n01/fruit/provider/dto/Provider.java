package cat.itacademy.s04.s02.n01.fruit.provider.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "providers", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
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

    @Getter
    @Setter
    public static class ProviderRequest {
        @NotBlank(message = "Name is required")
        private String name;

        @NotBlank(message = "Country is required")
        private String country;

    }

    public static class ProviderResponse {

        @Getter
        private Long id;
        @Getter
        private String name;

        @Getter
        private String country;

        public ProviderResponse(Long id, String name, String country) {
            this.name = name;
            this.country = country;
            this.id = id;
        }
    }
}