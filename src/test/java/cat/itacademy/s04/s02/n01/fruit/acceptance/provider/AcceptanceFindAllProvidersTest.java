package cat.itacademy.s04.s02.n01.fruit.acceptance.provider;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AcceptanceFindAllProvidersTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetListProviders() {
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

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/provider")
                .then()
                .statusCode(200)
                .body(not(empty()))
                .body("name", hasItems("Los manjares", "Las Frutas"));
    }

    @Test
    public void testListProvidersReturnEmptyListIfThereAreNotProvidersCreated() {
        given()
                .accept(ContentType.JSON)
                .when()
                .get("/provider")
                .then()
                .statusCode(200)
                .body("$", empty());
    }
}