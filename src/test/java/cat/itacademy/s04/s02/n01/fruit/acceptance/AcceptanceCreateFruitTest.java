package cat.itacademy.s04.s02.n01.fruit.acceptance;

import cat.itacademy.s04.s02.n01.fruit.provider.repository.ProviderRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AcceptanceCreateFruitTest {
    @LocalServerPort
    private int port;

    private static Long fruitId;

    @Autowired
    private ProviderRepository providerRepository;

    @BeforeAll
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
    public void testAddNewFruit() {
        String requestBody = """
                {
                  "name": "poma",
                  "weightInKilos": 1,
                  "providerName": "Las Frutas"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("providerName", "Las Frutas")
                .body(requestBody)
                .log().all()
                .when()
                .post("/fruits")
                .then()
                .statusCode(201)
                .extract()
                .response();


        int statusCode = response.statusCode();
        System.out.println("Created Fruit Status: " + statusCode);
    }

}
