package user;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import api.user.CreateUser;
import api.user.DeleteUser;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import pojo.user.User;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

public class DeleteUserTests {

        Faker fake = new Faker();
        SoftAssertions softly = new SoftAssertions();
        String appId = System.getenv("APP_ID");

        @BeforeAll
        static void log() {
                RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
        }

        @Test
        void verifyThatASuccessfulRequestReturnsA200StatusCode() {
                User user = new User();
                user.setLastName(fake.name().lastName());
                user.setFirstName(fake.name().firstName());
                user.setEmail(fake.internet().safeEmailAddress());
                CreateUser createUser = new CreateUser(appId, user);
                String userId = createUser.userResponse().getId();

                DeleteUser deleteUser = new DeleteUser(appId, userId);
                softly.assertThat(deleteUser.response().statusCode()).as("Status code").isEqualTo(200);
                softly.assertThat(deleteUser.deleteResponse().getId()).as("User id").isEqualTo(userId);
                softly.assertAll();
                assertThat(deleteUser.response().getBody().asString(), JsonSchemaValidator
                                .matchesJsonSchema(new File("src/main/java/schema/StringSchema.json")));
        }

        @Test
        void verifyThatARequestWithANonExistentUserIdReturnsA404StatusCode() {
                DeleteUser deleteUser = new DeleteUser(appId, "63ece0dad4d358a2b21af587");
                softly.assertThat(deleteUser.response().statusCode()).as("Status code").isEqualTo(404);
                softly.assertThat(deleteUser.error().getError()).as("Error message").isEqualTo("RESOURCE_NOT_FOUND");
                softly.assertAll();
                assertThat(deleteUser.response().getBody().asString(), JsonSchemaValidator
                                .matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
        }

        @Test
        void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
                DeleteUser deleteUser = new DeleteUser("null", "User Id");
                softly.assertThat(deleteUser.response().statusCode()).as("Status code").isEqualTo(403);
                softly.assertThat(deleteUser.error().getError()).as("Error message").isEqualTo("APP_ID_NOT_EXIST");
                softly.assertAll();
                assertThat(deleteUser.response().getBody().asString(), JsonSchemaValidator
                                .matchesJsonSchema(new File("src/main/java/schema/ErrorMessageSchema.json")));
        }
}
