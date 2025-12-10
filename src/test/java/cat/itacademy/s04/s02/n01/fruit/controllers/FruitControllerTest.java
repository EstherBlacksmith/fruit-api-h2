package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FruitService fruitService;

    @Test
    void createFruit_returnsFruitIfItsAddedCorrectly() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        when(fruitService.save(any(fruitRequest.getClass())))
                .thenReturn(new FruitResponse(1L, "Poma", 1));

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
    void createFruit_returnsErrorFruitIfINotFound() throws Exception {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        when(fruitService.save(any(fruitRequest.getClass())))
                .thenReturn(new FruitResponse(1L, "Poma", 1));

        ObjectMapper mapper = new ObjectMapper();
        String fruitJson = mapper.writeValueAsString(fruitRequest);

        mockMvc.perform(get("/fruits/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Poma"))
                .andExpect(jsonPath("$.weightInKilos").value(1));
    }
}

/*As the inventory manager,
I want to retrieve the details of a specific fruit using its identifier,
so that I can efficiently access information about a particular product.

Acceptance criteria:

If the ID exists, the system returns HTTP 200 OK with the fruit details.

If the ID does not exist, it returns HTTP 404 Not Found with an indicative message.*/




