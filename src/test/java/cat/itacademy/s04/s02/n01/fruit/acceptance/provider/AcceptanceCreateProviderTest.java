package cat.itacademy.s04.s02.n01.fruit.acceptance.provider;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class AcceptanceCreateProviderTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testAddNewProvider() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/provider")
                .then()
                .statusCode(201)
                .extract()
                .response();


        int statusCode = response.statusCode();
        System.out.println("Created provider Status: " + statusCode);
    }


    @Test
    public void testMustReturnErrorIfNameAlreadyExists() {
        String requestBody = """
                {
                  "name": "Los manjares",
                  "country": "Spain"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/provider")
                .then()
                .statusCode(201)
                .extract()
                .response();

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .log().all()
                .when()
                .post("/provider")
                .then()
                .statusCode(409)
                .extract()
                .response();
        int statusCode = response.statusCode();
        System.out.println("Created provider Status: " + statusCode);
    }

}
