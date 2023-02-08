import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.io.File;

public class DeleteUserTests {

    Faker fake = new Faker();

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        Response newUser = given()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"email\":\"" + fake.internet().safeEmailAddress() + "\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then()
                .statusCode(200)
                .extract()
                .response();

        String userId = newUser.path("id").toString();

        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .delete("https://dummyapi.io/data/v1/user/" + userId)
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/StringSchema.json")));
    }

    @Test
    void verifyThatARequestWithANonExistentUserIdReturnsA404StatusCode() {

        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .delete("https://dummyapi.io/data/v1/user/" + "63e39acb1b7900706240b6a9")
                .then()
                .statusCode(404)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        given().header("app-id", "63d1caa34808707205700000")
                .header("Content-Type", "application/json")
                .delete("https://dummyapi.io/data/v1/user/" + "63e39acb1b7900706240b6a9")
                .then().statusCode(403)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }
}
