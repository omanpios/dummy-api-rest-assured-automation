import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import api.UpdateUser;
import io.restassured.module.jsv.JsonSchemaValidator;
import pojo.Location;
import pojo.User;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UpdateUserTests {
        Faker fake = new Faker();
        SoftAssertions softly = new SoftAssertions();

        @Test
        void verifyThatASuccessfulRequestReturnsA200StatusCode() {
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
                user.setFirstName(fake.name().firstName());
                user.setLastName(fake.name().lastName());
                user.setTitle(randomTitle);
                user.setGender(randomGender);
                user.setDateOfBirth(dob);
                user.setEmail(fake.internet().safeEmailAddress("omanpios"));
                user.setPicture(fake.internet().image());
                location.setStreet(fake.address().fullAddress());
                location.setCity(fake.address().city());
                location.setState(fake.address().state());
                location.setCountry(fake.address().country());
                location.setTimezone(fake.address().timeZone());
                user.setLocation(location);
                user.setPhone(fake.phoneNumber().cellPhone());

                UpdateUser updateUser = new UpdateUser("63d1caa3480870720570afb7", user, "60d0fe4f5311236168a10a2c");

                softly.assertThat(updateUser.response().statusCode()).as("Status code").isEqualTo(200);
                softly.assertAll();
        }

        @Test
        void verifyThatResponseBodyMatchesJsonSchemaForASuccessfulRequest() {
                User user = new User();
                user.setFirstName(fake.name().firstName());

                UpdateUser updateUser = new UpdateUser("63d1caa3480870720570afb7", user, "60d0fe4f5311236168a10a2c");

                assertThat(updateUser.response().getBody().asString(), JsonSchemaValidator
                                .matchesJsonSchema(new File("src/main/java/schema/GetUserByIdSchema.json")));
        }

        @Test
        void verifyThatARequestWithANonExistentUserIdReturnsA400StatusCode() {
                User user = new User();
                user.setFirstName(fake.name().firstName());

                UpdateUser updateUser = new UpdateUser("63d1caa3480870720570afb7", user, "60d0fe4f5311236168a10000");

                softly.assertThat(updateUser.response().statusCode()).as("Status code").isEqualTo(400);
                softly.assertAll();
        }

        @Test
        void verifyThatResponseBodyMatchesJsonSchemaForAnInvalidRequest() {
                User user = new User();
                user.setFirstName(fake.name().firstName());

                UpdateUser updateUser = new UpdateUser("63d1caa3480870720570afb7", user, "60d0fe4f5311236168a10000");

                assertThat(updateUser.response().getBody().asString(), JsonSchemaValidator
                                .matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
        }

        @Test
        void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
                User user = new User();
                user.setFirstName(fake.name().firstName());

                UpdateUser updateUser = new UpdateUser("63d1caa34808707205700000", user, "60d0fe4f5311236168a10000");

                softly.assertThat(updateUser.response().statusCode()).as("Status code").isEqualTo(403);
                softly.assertThat(updateUser.error().getError()).as("Error message").isEqualTo("APP_ID_NOT_EXIST");
                softly.assertAll();
        }
}
