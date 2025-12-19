package cat.itacademy.s04.s02.n01.fruit.acceptance.fruit;

import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AcceptanceDeleteFruitTests {
    @LocalServerPort
    private int port;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    public void setup() {
        providerRepository.deleteAll();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        String providerRequest = """
                {
                  "name": "Las Frutas",
                  "country": "Spain"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(providerRequest)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);
    }

    @Test
    public void testDeleteFruitByIdReturnNoContent() {
        String requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .queryParam("providerName", "Las Frutas")
                .when()
                .post("/fruits")
                .then()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .log().all()
                .when()
                .delete("/fruits/{id}")
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test
    public void testDeleteFruitByIdReturnErrorIfFruitDOesNOtExists() {
        String requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .queryParam("providerName", "Las Frutas")
                .when()
                .post("/fruits")
                .then()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 3)
                .log().all()
                .when()
                .delete("/fruits/{id}")
                .then()
                .statusCode(404);
    }

}