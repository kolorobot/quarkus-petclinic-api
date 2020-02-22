package pl.codeleak.samples.petclinic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class VisitResourceTest {

    @Test
    public void pagedList() {
        given()
                .when().get("/visits?pageNum=0&pageSize=2")
                .then()
                .statusCode(200)
                .body(
                        "$.size()", is(2),
                        "date", containsInAnyOrder("2013/01/01 00:00", "2013/01/02 00:00"),
                        "pet.name", containsInAnyOrder("Samantha", "Max")
                );
    }
}