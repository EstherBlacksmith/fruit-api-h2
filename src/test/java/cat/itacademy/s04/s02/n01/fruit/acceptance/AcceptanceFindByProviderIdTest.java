package cat.itacademy.s04.s02.n01.fruit.acceptance;


import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AcceptanceFindByProviderIdTest {
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
    public void testGetFruitByProviderIdReturnDetailsIfIdExists() {

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
                .log().all()
                .when()
                .post("/fruits")
                .then()
                .log().all()
                .statusCode(201);

        String requestBody2 = """
                {
                  "name": "taronja",
                  "weightInKilos": 10,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody2)
                .queryParam("providerName", "Las Frutas")
                .when()
                .post("/fruits")
                .then()
                .statusCode(201);


        given()
                .accept(ContentType.JSON)
                .pathParam("id", 1L)
                .log().all()
                .when()
                .get("/fruits/providerId/{id}")
                .then()
                .statusCode(200)
                .body( not(empty()))
                .body("name", hasItems("poma", "taronja"))
                .body("weightInKilos", hasItems(1, 1));
    }

    @Test
    public void testGetFruitByIdReturnErrorIfProviderDoesNotExists() {
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

        requestBody = """
                {
                  "name": "Taronja",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .queryParam("providerName", "Las Frutas")
                .body(requestBody)
                .when()
                .post("/fruits")
                .then()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/fruits/providerId/{id}", 8L)
                .then()
                .log().all()
                .statusCode(404);

    }
}
