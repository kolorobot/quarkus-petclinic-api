package pl.codeleak.samples.petclinic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class SpecialtyResourceTest {

    @Test
    public void listAll() {
        given()
                .when().get("/specialties")
                .then()
                .statusCode(200)
                .body(
                        "$.size()", is(3),
                        "name", containsInAnyOrder("radiology", "surgery", "dentistry")
                );
    }
}