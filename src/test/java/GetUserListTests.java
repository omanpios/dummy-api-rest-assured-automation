import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;

import java.io.File;

import api.GetUserList;
import io.restassured.module.jsv.JsonSchemaValidator;

public class GetUserListTests {
    SoftAssertions softly = new SoftAssertions();

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        GetUserList getUserList = new GetUserList(1, 10);
        softly.assertThat(getUserList.response().statusCode()).isEqualTo(200);
        softly.assertAll();
    }

    @Test
    void verifyThatTheApiImplementsPagination() {
        GetUserList getUserList = new GetUserList(0, 20);
        softly.assertThat(getUserList.response().getBody().path("limit").toString()).isEqualTo("20");
        softly.assertThat(getUserList.response().getBody().path("total").toString()).isNotEmpty();
        softly.assertThat(getUserList.response().getBody().path("page").toString()).isNotEmpty();
        softly.assertAll();
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        given()
                .header("app-id", "63d1caa3480870720572222")
                .get("https://dummyapi.io/data/v1/user")
                .then().statusCode(403)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }

    @Test
    void verifyThatTheResponseBodyMatchesTheJsonSchema() {
        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .get("https://dummyapi.io/data/v1/user")
                .then()
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/GetListUserSchema.json")));
    }
}
