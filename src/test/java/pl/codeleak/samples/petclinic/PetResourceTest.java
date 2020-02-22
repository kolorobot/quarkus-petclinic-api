package pl.codeleak.samples.petclinic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class PetResourceTest {

    @Test
    public void pagedList() {
        given()
                .when().get("/pets?pageNum=0&pageSize=2")
                .then()
                .statusCode(200)
                .body(
                        "$.size()", is(2),
                        "name", containsInAnyOrder("Leo", "Basil")
                );
    }
}