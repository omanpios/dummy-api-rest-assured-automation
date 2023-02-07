import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import io.restassured.module.jsv.JsonSchemaValidator;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateUserTests {

        Faker fake = new Faker();

        @Test
        void verifyThatASuccessfulRequestBasicDataReturnsA200StatusCode() {
                given()
                                .header("app-id", "63d1caa3480870720570afb7")
                                .header("Content-Type", "application/json")
                                .body("{\n\"lastName\":\"" + fake.name().lastName()
                                                + "\",\n\"firstName\":\"" + fake.name().firstName()
                                                + "\",\n\"email\":\"" + fake.internet().safeEmailAddress() + "\"\n}")
                                .post("https://dummyapi.io/data/v1/user/create")
                                .then()
                                .statusCode(200)
                                .body(JsonSchemaValidator.matchesJsonSchema(
                                                new File("src/main/java/schema/GetUserByIdSchema.json")));
        }

        @Test
        void verifyThatASuccessfulRequestFullDataReturnsA200StatusCode() {
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
                                .post("https://dummyapi.io/data/v1/user/create")
                                .then()
                                .statusCode(200)
                                .body(JsonSchemaValidator.matchesJsonSchema(
                                                new File("src/main/java/schema/GetUserByIdSchema.json")));
        }

        @Test
        void verifyThatARequestWithAnExistentEmailAddressReturnsA400StatusCode() {
                given()
                                .header("app-id", "63d1caa3480870720570afb7")
                                .header("Content-Type", "application/json")
                                .body("{\n\"lastName\":\"" + fake.name().lastName()
                                                + "\",\n\"firstName\":\"" + fake.name().firstName()
                                                + "\",\n\"email\":\"lance.foster@example.com\"\n}")
                                .post("https://dummyapi.io/data/v1/user/create")
                                .then()
                                .statusCode(400)
                                .body(JsonSchemaValidator.matchesJsonSchema(
                                                new File("src/main/java/schema/ErrorMessageSchema.json")));
        }

        @Test
        void verifyThatAnInvalidRequestReturnsA400StatusCode() {
                given()
                                .header("app-id", "63d1caa3480870720570afb7")
                                .header("Content-Type", "application/json")
                                .body("{\n\"lastName\":\"D\",\n\"firstName\":\"C\",\n\"email\":\"lance.foster\"\n}")
                                .post("https://dummyapi.io/data/v1/user/create")
                                .then()
                                .statusCode(400)
                                .body(JsonSchemaValidator.matchesJsonSchema(
                                                new File("src/main/java/schema/ErrorMessageSchema.json")));
        }
}
