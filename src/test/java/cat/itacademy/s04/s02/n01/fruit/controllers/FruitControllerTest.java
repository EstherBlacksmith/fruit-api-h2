package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.fruit.controller.FruitController;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.fruit.dto.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.InvalidFruitRequestException;
import cat.itacademy.s04.s02.n01.fruit.fruit.service.FruitService;
import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import cat.itacademy.s04.s02.n01.fruit.provider.service.ProviderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FruitController.class)
class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FruitService fruitService;
    @MockBean
    private ProviderService providerService;

    private Provider provider;

    @BeforeEach
    void setUp(){
        Provider.ProviderRequest providerRequest = new Provider.ProviderRequest();
        providerRequest.setName("Las Frutas");
        providerRequest.setCountry("Spain");
        provider = new Provider(providerRequest.getName(),providerRequest.getCountry());
        when(providerService.save(any(Provider.ProviderRequest.class)))
                .thenReturn(new Provider.ProviderResponse(1L,providerRequest.getName(),providerRequest.getCountry()));

    }

    @Test
    void createFruit_returnsFruitIfItsAddedCorrectly() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);
        fruitRequest.setProviderName("Las Frutas");

        when(fruitService.save(any(fruitRequest.getClass()),eq(provider.getName())))
                .thenReturn(new FruitResponse(1L, "Poma", 1,"Las Frutas"));

        ObjectMapper mapper = new ObjectMapper();
        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(post("/fruits")
                        .param("providerName", "Las Frutas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value(1));

        Mockito.verify(fruitService, times(1))
                .save(any(FruitRequest.class), eq(provider.getName()));
    }

    @Test
    void createFruit_returnsErrorFruitIfItsNotAddedCorrectly() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(0);

        when(fruitService.save(any(fruitRequest.getClass()),eq(provider.getName())))
                .thenReturn(new FruitResponse(1L, "Poma", 0,provider.getName()));

        ObjectMapper mapper = new ObjectMapper();
        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void retrieveFruit_returnsFruitIfExists() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        Fruit fruit = new Fruit(fruitRequest,provider);
        when(fruitService.get(1L)).thenReturn(fruit);

        mockMvc.perform(get("/fruits/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value(1));

    }


    @Test
    void createFruit_returnsErrorIfFruitItsNotFound() throws Exception {
        when(fruitService.get(eq(Long.valueOf(2L))))
                .thenThrow(new FruitNotFoundException("Fruit doesn't exist"));

        mockMvc.perform(get("/fruits/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Fruit doesn't exist"));

    }

    @Test
    void getFruits_returnsListIfAreFruitsInThInventoryANdhTTP200() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        Fruit fruit = new Fruit(fruitRequest,provider);
        List<Fruit> listFruit = new ArrayList<>();
        listFruit.add(fruit);

        when(fruitService.getAll()).thenReturn(listFruit);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(result -> listFruit.contains(fruit));

    }

    @Test
    void getFruits_returnsEmptyListIfArentFruitsInThInventoryANdhTTP200() throws Exception {
        List<Fruit> listFruit = new ArrayList<>();

        when(fruitService.getAll()).thenReturn(listFruit);

        mockMvc.perform(get("/fruits"))
                .andExpect(status().isOk())
                .andExpect(result -> listFruit.isEmpty());
    }

    @Test
    void updateFruit_returnErrorIfTheFruitDoesNotExists() throws Exception {
        when(fruitService.update(eq(Long.valueOf(3L)), any(FruitRequest.class)))
                .thenThrow(new FruitNotFoundException("Fruit doesn't exist"));

        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Taronja");
        fruitRequest.setWeightInKilos(11);
        fruitRequest.setProviderName("Las Frutas");

        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);
        mockMvc.perform(put("/fruits/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isNotFound())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Fruit doesn't exist"));

    }

    @Test
    void updateFruit_returnErrorIfTheDataIsNotValid() throws Exception {
        when(fruitService.update(eq(Long.valueOf(3L)), any(FruitRequest.class)))
                .thenThrow(new InvalidFruitRequestException("The given data are invalids"));
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Potatoes$$");
        fruitRequest.setWeightInKilos(111111);
        fruitRequest.setProviderName("Las Frutas");

        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(put("/fruits/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isBadRequest())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("The given data are invalids"));

    }

    @Test
    void updateFruit_returnOkAndTheUpdatedFruitIfTheDataIsValidAndTheFruitExists() throws Exception {

        when(fruitService.update(eq(Long.valueOf(1L)), any(FruitRequest.class)))
                .thenReturn(new FruitResponse(1L, "Taronja", 11,provider.getName()));

        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Taronja");
        fruitRequest.setWeightInKilos(11);
        fruitRequest.setProviderName("Las Frutas");
        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(put("/fruits/{id}", 1L)
                        .param("providerName", "Las Frutas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Taronja"))
                .andExpect(jsonPath("$.weightInKilos").value(11));
    }

    @Test
    void deleteFruit_returnErrorIfTheFruitDoesNotExists() throws Exception {
        when(fruitService.delete(1L)).thenThrow(new FruitNotFoundException("Fruit doesn't exist"));

        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Taronja");
        fruitRequest.setWeightInKilos(11);
        fruitRequest.setProviderName("Las Frutas");

        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(delete("/fruits/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isNotFound())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Fruit doesn't exist"));

    }
    @Test
    void deleteFruit_returnNoContentIfTheFruitIsDeleted() throws Exception {
        when(fruitService.delete(1L)).thenReturn(HttpStatus.NO_CONTENT);

        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Taronja");
        fruitRequest.setWeightInKilos(11);
        fruitRequest.setProviderName("Las Frutas");

        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(delete("/fruits/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isNoContent());

    }
}
