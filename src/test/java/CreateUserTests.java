import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTests {
    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        given().log().all()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n    \"lastName\": \"Murphy\",\n    \"firstName\": \"Taryn\",\n    \"email\": \"Carmela_Zboncak@example.net\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then().log().all()
                .statusCode(200);
    }
    //TODO Add random data
    // - Add tests for an existing user, fields validations, response validation, schema validation
}
