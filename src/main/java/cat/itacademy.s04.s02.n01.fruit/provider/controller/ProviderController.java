package cat.itacademy.s04.s02.n01.fruit.provider.controller;

import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/provider")
    public ResponseEntity<Provider.ProviderResponse> createProvider(
            @Valid @RequestBody Provider.ProviderRequest providerRequest) {

        Provider.ProviderResponse providerResponse = providerService.save(providerRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(providerResponse);

    }

    //TODO GET	/providers	Llistar proveïdors

    @GetMapping("/provider")
    public ResponseEntity<List<Provider>> getProvider() {

        List<Provider> listProvider = providerService.getAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listProvider);
    }

    //TODO PUT	/providers/{id}	Actualitzar proveïdor

    @PutMapping("/provider/{id}")
    public ResponseEntity<ProviderResponse> updateFruit(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequest providerRequest) {

        ProviderResponse providerResponse = providerService.update(id, providerRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(providerResponse);
    }

    //TODO DELETE	/providers/{id}	Eliminar proveïdor
    @DeleteMapping("/provider/{id}")
    public ResponseEntity<Provider> deleteProvider(
            @PathVariable Long id) {

        Provider providerResponse = providerService.get(id);
        providerService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(providerResponse);
    }
}
