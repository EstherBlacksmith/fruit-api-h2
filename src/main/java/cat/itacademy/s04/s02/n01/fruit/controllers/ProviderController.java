package cat.itacademy.s04.s02.n01.fruit.controllers;


import cat.itacademy.s04.s02.n01.fruit.model.ProviderRequest;
import cat.itacademy.s04.s02.n01.fruit.model.ProviderResponse;
import cat.itacademy.s04.s02.n01.fruit.services.ProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    //TODO POST	/providers	Crear proveïdor
    @RequestMapping("/provider")
    public ResponseEntity<ProviderResponse> createProvider(
            ProviderRequest providerRequest) {

        ProviderResponse providerResponse = providerService.save(providerRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerResponse);

    }

    //TODO GET	/providers	Llistar proveïdors
    //TODO PUT	/providers/{id}	Actualitzar proveïdor
    //TODO GET	/fruits?providerId={id}	Obtenir fruites d’un proveïdor
    //TODO DELETE	/providers/{id}	Eliminar proveïdor
}
