package cat.itacademy.s04.s02.n01.fruit.controllers;

import cat.itacademy.s04.s02.n01.fruit.model.FruitRequest;
import cat.itacademy.s04.s02.n01.fruit.model.FruitResponse;
import cat.itacademy.s04.s02.n01.fruit.services.FruitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FruitController.class)
class FruitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FruitService fruitService;

    @Test
    void createFruit_returnsFruitIfItsAddedCorrectly() {
        FruitRequest fruitRequest = new FruitRequest();
        fruitRequest.setName("Poma");
        fruitRequest.setWeightInKilos(1);

        when(fruitService.save(any(fruitRequest.getClass())))
                .thenReturn(new FruitResponse(1L, "Poma", 1));

        ObjectMapper mapper = new ObjectMapper();
        String fruitJson = mapper.writeValueAsString(fruitRequest);


        try {
            mockMvc.perform(post("/fruits")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(fruitJson))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.name").value("Poma"))
                    .andExpect(jsonPath("$.weightInKilos").value(1));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}