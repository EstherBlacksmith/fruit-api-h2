package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.exception.InvalidFruitRequestException;
import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(FruitController.class)
class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FruitService fruitService;

    @Test
    void createFruit_returnsFruitIfItsAddedCorrectly() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        when(fruitService.save(any(fruitRequest.getClass())))
                .thenReturn(new FruitResponse(eq(Long.valueOf(1L)), "Poma", 1));

        ObjectMapper mapper = new ObjectMapper();
        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(post("/fruits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value(1));
    }

    @Test
    void createFruit_returnsErrorFruitIfItsNotAddedCorrectly() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(0);

        when(fruitService.save(any(fruitRequest.getClass())))
                .thenReturn(new FruitResponse(1L, "Poma", 0));

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

        Fruit fruit = new Fruit(fruitRequest);
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

        Fruit fruit = new Fruit(fruitRequest);
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
                .thenReturn(new FruitResponse(1L, "Taronja", 11));

        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Taronja");
        fruitRequest.setWeightInKilos(11);
        ObjectMapper mapper = new ObjectMapper();

        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(put("/fruits/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fruitJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Taronja"))
                .andExpect(jsonPath("$.weightInKilos").value(11));
    }
}

