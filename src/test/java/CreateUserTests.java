import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import api.CreateUser;
import io.restassured.module.jsv.JsonSchemaValidator;
import pojo.Location;
import pojo.User;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CreateUserTests {

        Faker fake = new Faker();
        SoftAssertions softly = new SoftAssertions();
        String appId = System.getenv("APP_ID");

        @Test
        void verifyThatASuccessfulRequestBasicDataReturnsA200StatusCode() {
                User user = new User();
                user.setFirstName("Omar");
                user.setLastName(fake.name().lastName());
                user.setEmail(fake.internet().safeEmailAddress());
                System.out.println(user.getFirstName());
                System.out.println(appId);
                CreateUser createUser = new CreateUser(appId, user);

                softly.assertThat(createUser.response().statusCode()).as("Status code").isEqualTo(200);
                softly.assertAll();
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

                User user = new User();
                Location location = new Location();
                user.setTitle(randomTitle);
                user.setFirstName(fake.name().firstName());
                user.setLastName(fake.name().lastName());
                user.setGender(randomGender);
                user.setEmail(fake.internet().safeEmailAddress());
                user.setDateOfBirth(dob);
                user.setPhone(fake.phoneNumber().cellPhone());
                user.setPicture(fake.internet().image());
                location.setStreet(fake.address().fullAddress());
                location.setCity(fake.address().city());
                location.setState(fake.address().state());
                location.setCountry(fake.address().country());
                location.setTimezone(fake.address().timeZone());
                user.setLocation(location);
                CreateUser createUser = new CreateUser("63d1caa3480870720570afb7", user);
                softly.assertThat(createUser.response().statusCode()).as("Status code").isEqualTo(200);
                softly.assertAll();
        }

        @Test
        void verifyThatResponseBodyMatchesJsonSchema() {
                File userSchema = new File("src/main/java/schema/GetUserByIdSchema.json");
                User user = new User();
                user.setFirstName(fake.name().firstName());
                user.setLastName(fake.name().lastName());
                user.setEmail(fake.internet().safeEmailAddress());
                CreateUser createUser = new CreateUser("63d1caa3480870720570afb7", user);
                assertThat(createUser.response().getBody().asString(),
                                JsonSchemaValidator.matchesJsonSchema(userSchema));
        }

        @Test
        void verifyThatARequestWithAnExistentEmailAddressReturnsA400StatusCode() {
                File errorSchema = new File("src/main/java/schema/ErrorMessageSchema.json");
                User user = new User();
                user.setFirstName(fake.name().firstName());
                user.setLastName(fake.name().lastName());
                user.setEmail("lance.foster@example.com");
                CreateUser createUser = new CreateUser("63d1caa3480870720570afb7", user);
                softly.assertThat(createUser.response().statusCode()).as("Status code").isEqualTo(400);
                softly.assertAll();
                assertThat(createUser.response().getBody().asString(),
                                JsonSchemaValidator.matchesJsonSchema(errorSchema));
        }

        @Test
        void verifyThatAnInvalidRequestReturnsA400StatusCode() {
                File errorSchema = new File("src/main/java/schema/ErrorMessageSchema.json");
                User user = new User();
                user.setFirstName("D");
                user.setLastName("E");
                user.setEmail("lance.foster@");
                CreateUser createUser = new CreateUser("63d1caa3480870720570afb7", user);
                softly.assertThat(createUser.response().statusCode()).as("Status code").isEqualTo(400);
                softly.assertAll();
                assertThat(createUser.response().getBody().asString(),
                                JsonSchemaValidator.matchesJsonSchema(errorSchema));
        }
}
