import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

import java.io.File;

import api.GetUserList;
import io.restassured.module.jsv.JsonSchemaValidator;

public class GetUserByIdTests {
    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        GetUserList getUserList = new GetUserList(0, 5);
        String userId = getUserList.response().getBody().path("data[0].id");
        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .get("https://dummyapi.io/data/v1/user/" + userId)
                .then()
                .statusCode(200);
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        GetUserList getUserList = new GetUserList(0, 5);
        String userId = getUserList.response().getBody().path("data[0].id");
        given()
                .header("app-id", "63d1caa3480870720572222")
                .get("https://dummyapi.io/data/v1/user/" + userId)
                .then()
                .statusCode(403);
    }

    @Test
    void verifyThatTheResponseBodyMatchesTheJsonSchema() {
        GetUserList getUserList = new GetUserList(0, 5);
        String userId = getUserList.response().getBody().path("data[0].id");
        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .get("https://dummyapi.io/data/v1/user/" + userId)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/GetUserByIdSchema.json")));
    }

}
