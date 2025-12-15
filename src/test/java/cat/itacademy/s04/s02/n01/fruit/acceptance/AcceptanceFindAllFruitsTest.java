package cat.itacademy.s04.s02.n01.fruit.acceptance;

import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AcceptanceFindAllFruitsTest {
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
                .contentType("application/json")
                .body(providerRequest)
                .when()
                .post("/provider")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void testListFruitsReturnEmptyListIFThereAreNotFruitsCreated() {
        given()
                .accept("application/json")
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body("$", empty());
    }

    @Test
    public void testListFruitsNotEmptyAfterCreation() {

        String requestBody = """
        {
            "name": "poma",
            "weightInKilos": 1
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .queryParam("providerName", "Las Frutas")
                .body(requestBody)
                .when()
                .post("/fruits")
                .then()
                .log().all()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body( not(empty()))
                .body("name", notNullValue())
                .body("weightInKilos", notNullValue());
    }

    @Test
    public void testGetListFruits() {
        String requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1
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

        requestBody = """
                {
                  "name": "Taronja",
                  "weightInKilos": 1
                }
                """;

        given()
                .contentType("application/json")
                .queryParam("providerName", "Las Frutas")
                .body(requestBody)
                .when()
                .post("/fruits")
                .then()
                .statusCode(201);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body( not(empty()));

       given()
                .accept(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body( not(empty()))
                .body("name", hasItems("poma", "Taronja"))
                .body("weightInKilos", hasItems(1, 1));;

        Response response =
                given()
                .accept(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .extract()
                .response();

        System.out.println(response.prettyPrint());
    }

}
