package cat.itacademy.s04.s02.n01.fruit.model;

import lombok.Getter;

public class ProviderResponse {

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
