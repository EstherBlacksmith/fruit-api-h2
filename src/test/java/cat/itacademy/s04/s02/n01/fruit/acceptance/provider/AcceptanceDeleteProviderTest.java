package cat.itacademy.s04.s02.n01.fruit.acceptance.provider;

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
class AcceptanceDeleteProviderTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeEach
    public void setup() {
        providerRepository.deleteAll();
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testDeleteProviderByIdReturnNoContent() {
        String requestBody = """
                {
                  "name": "Fruticas",
                  "country": "Morocco"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .log().all()
                .when()
                .delete("/provider/{id}")
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test
    public void testDeleteProviderByIdReturnErrorIfProviderDoesNotExists() {
        String requestBody = """
                {
                  "name": "Fruticas",
                  "country": "Morocco"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);


        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 3)
                .log().all()
                .when()
                .delete("/provider/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testDeleteProviderByIdReturnErrorIfProviderHasAssociatedFruits() {
        String requestBody = """
                {
                  "name": "Fruticas",
                  "country": "Morocco"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        String requestBodyFruits = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Fruticas"
                }
                """;

   given()
                .contentType(ContentType.JSON)
                .queryParam("providerName", "Fruticas")
                .body(requestBodyFruits)
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
                .delete("/provider/{id}")
                .then()
                .statusCode(409);
    }
}