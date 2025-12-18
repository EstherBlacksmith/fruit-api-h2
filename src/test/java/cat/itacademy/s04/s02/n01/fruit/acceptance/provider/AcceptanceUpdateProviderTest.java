package cat.itacademy.s04.s02.n01.fruit.acceptance.provider;

import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.h2.util.TempFileDeleter;
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
class AcceptanceUpdateProviderTest {
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
    public void testUpdateProviderByIdReturnDetailsIfIdExists() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        requestBody = """
                {
                  "name": "Las Frutas2",
                  "country": "Belgium"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .body(requestBody)
                .log().all()
                .when()
                .put("/provider/{id}")
                .then()
                .statusCode(200)
                .body("name",equalTo("Las Frutas2"))
                .body("country", equalTo("Belgium"));
    }

    @Test
    public void testUpdateProviderByIdReturnErrorIfIDoesNotExists() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        requestBody = """
                {
                  "name": "Las Frutas",
                  "country": "Belgium"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 2)
                .body(requestBody)
                .log().all()
                .when()
                .put("/provider/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    public void testUpdateProviderByIdReturnErrorIfNameAlreadyExists() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Belgium"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .body(requestBody)
                .log().all()
                .when()
                .put("/provider/{id}")
                .then()
                .statusCode(409);


    }

    @Test
    public void testUpdateProviderByIdReturnErrorIfNameIsEmpty() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/provider")
                .then()
                .statusCode(201);

        requestBody = """
                {
                  "name": "",
                  "country": "Belgium"
                }
                """;

        given()
                .accept(ContentType.JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", 1)
                .body(requestBody)
                .log().all()
                .when()
                .put("/provider/{id}")
                .then()
                .statusCode(400);


    }
}