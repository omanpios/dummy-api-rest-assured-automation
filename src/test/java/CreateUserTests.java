import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

import java.io.File;

public class CreateUserTests {

    Faker fake = new Faker();

    @Test
    void verifyThatASuccessfulRequestBasicDataReturnsA200StatusCode() {
        given().log().all()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"email\":\"" + fake.internet().safeEmailAddress() + "\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then().log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/GetUserByIdSchema.json")));
    }

    @Test
    void verifyThatASuccessfulRequestFullDataReturnsA200StatusCode() {
        given().log().all()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"email\":\"" + fake.internet().safeEmailAddress()
                        + "\",\n\"picture\":\"" + fake.internet().image()
                        + "\",\n\"location\": \n {\"street\": \"Street\",\n\"city\": \"Bta\",\n\"state\": \"Street\",\n\"country\": \"Street\",\n\"timezone\": \"9:00\"},\n\"phone\":\""
                        + fake.phoneNumber().cellPhone() + "\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then().log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/GetUserByIdSchema.json")));
    }

    @Test
    void verifyThatARequestWithAnExistentEmailAddressReturnsA400StatusCode() {
        given().log().all()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"email\":\"lance.foster@example.com\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then().log().all()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }

    @Test
    void verifyThatAnIncalidRequestReturnsA400StatusCode() {
        given().log().all()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"D\",\n\"firstName\":\"C\",\n\"email\":\"lance.foster\"\n}")
                .post("https://dummyapi.io/data/v1/user/create")
                .then().log().all()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }
}
// TODO: title, gender, birthdate(IsoDate), fake hardcoded values