import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UpdateUserTests {
    Faker fake = new Faker();

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        String userId = "60d0fe4f5311236168a10a2c";

        String dob = Instant.ofEpochMilli(fake.date().birthday(18, 99).getTime()).toString();
        Random random = new Random();
        List<String> title = Arrays.asList("mr", "ms", "mrs", "miss", "dr");
        List<String> gender = Arrays.asList("male", "female", "other");

        int randomItemFromTitle = random.nextInt(title.size());
        String randomTitle = title.get(randomItemFromTitle);

        int randomItemFromGender = random.nextInt(gender.size());
        String randomGender = gender.get(randomItemFromGender);
        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"title\":\"" + randomTitle
                        + "\",\n\"gender\":\"" + randomGender
                        + "\",\n\"dateOfBirth\":\"" + dob
                        + "\",\n\"email\":\"" + fake.internet().safeEmailAddress()
                        + "\",\n\"picture\":\"" + fake.internet().image()
                        + "\",\n\"location\": \n {\"street\": \"" + fake.address().fullAddress()
                        + "\",\n\"city\": \"" + fake.address().cityName()
                        + "\",\n\"state\": \"" + fake.address().state()
                        + "\",\n\"country\": \"" + fake.address().country()
                        + "\",\n\"timezone\": \"" + fake.address().timeZone()
                        + "\"},\n\"phone\":\""
                        + fake.phoneNumber().cellPhone() + "\"\n}")
                .put("https://dummyapi.io/data/v1/user/" + userId)
                .then().log().all()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/GetUserByIdSchema.json")));
    }

    @Test
    void verifyThatARequestWithANonExistentUserIdReturnsA400StatusCode() {
        given()
                .header("app-id", "63d1caa3480870720570afb7")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"email\":\"" + fake.internet().safeEmailAddress()
                        + "\",\n\"picture\":\"" + fake.internet().image()
                        + "\",\n\"location\": \n {\"street\": \"" + fake.address().fullAddress()
                        + "\",\n\"city\": \"" + fake.address().cityName()
                        + "\",\n\"state\": \"" + fake.address().state()
                        + "\",\n\"country\": \"" + fake.address().country()
                        + "\",\n\"timezone\": \"" + fake.address().timeZone()
                        + "\"},\n\"phone\":\""
                        + fake.phoneNumber().cellPhone() + "\"\n}")
                .put("https://dummyapi.io/data/v1/user/" + "nonexistentuserid")
                .then().log().all()
                .statusCode(400)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        given()
                .header("app-id", "63d1caa34808707205700000")
                .header("Content-Type", "application/json")
                .body("{\n\"lastName\":\"" + fake.name().lastName()
                        + "\",\n\"firstName\":\"" + fake.name().firstName()
                        + "\",\n\"phone\":\""
                        + fake.phoneNumber().cellPhone() + "\"\n}")
                .put("https://dummyapi.io/data/v1/user/" + "60d0fe4f5311236168a10a2c")
                .then()
                .statusCode(403)
                .body(JsonSchemaValidator.matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
    }
}
