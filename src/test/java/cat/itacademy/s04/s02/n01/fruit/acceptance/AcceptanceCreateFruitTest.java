package cat.itacademy.s04.s02.n01.fruit.acceptance;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class AcceptanceCreateFruitTest {

    private static Long fruitId;

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/";
    }
/*   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @NotBlank(message = "Name is required")
    private String name;
    @Setter
    @Positive(message = "Kilos must be at least 1")
    private int weightInKilos;
    @Setter
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
*/

    @Test
    public void testAddNewFruit() {
        String requestBody = """
        {
          "id": 4677,
          "name": "poma",
          "weightInKilos": "1",
          "provider": { "id": 9342,
            "name": "Los Fruitis".
            "country": "Spain"
            }
        }
        """;
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/fruits")
                .then()
                .statusCode(200)
                .body("name", equalTo("poma"))
                .extract().response();

        fruitId = response.jsonPath().getLong("id");
        System.out.println("Created Fruit ID: " + fruitId);
    }

}
