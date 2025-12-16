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
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AcceptanceUpdateFruitTest {
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
    public void testUpdateFruitByIdReturnDetailsIfIdExists() {
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
                .statusCode(201);

        requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .body(requestBody)
                .log().all()
                .when()
                .put("/fruits/{id}")
                .then()
                .statusCode(200)
                .body( not(empty()))
                .body("name", notNullValue())
                .body("weightInKilos", notNullValue());
    }

    @Test
    public void testUpdateFruitByIdReturnErrorIfFruitDoesNotExists() {
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
                .statusCode(201);

        requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 2)
                .body(requestBody)
                .log().all()
                .when()
                .put("/fruits/{id}")
                .then()
                .statusCode(404);
    }


    @Test
    public void testUpdateFruitByIdReturnErrorIfProviderDoesNotExists() {
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
                .statusCode(201);

        requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Comas"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .body(requestBody)
                .log().all()
                .when()
                .put("/fruits/{id}")
                .then()
                .statusCode(500);
    }
}
