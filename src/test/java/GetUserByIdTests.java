import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;

import api.GetUser;
import api.GetUserList;
import io.restassured.module.jsv.JsonSchemaValidator;

public class GetUserByIdTests {
    String appId = "63d1caa3480870720570afb7";
    SoftAssertions softly = new SoftAssertions();

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        GetUserList getUserList = new GetUserList(null, null, appId);
        String userId = getUserList.response().getBody().path("data[0].id");

        GetUser getUser = new GetUser(appId, userId);
        softly.assertThat(getUser.response().statusCode()).as("Status code").isEqualTo(200);
        softly.assertAll();
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        GetUserList getUserList = new GetUserList(null, null, appId);
        String userId = getUserList.response().getBody().path("data[0].id");

        GetUser getUser = new GetUser("appId", userId);
        softly.assertThat(getUser.response().statusCode()).as("Status code").isEqualTo(403);
        softly.assertThat(getUser.error().getError()).as("Validation error").isEqualTo("APP_ID_NOT_EXIST");
        softly.assertAll();
    }

    @Test
    void verifyThatANonExistentUserIdReturnsA404StatusCode() {
        GetUser getUser = new GetUser(appId, "63d1caa3480870720570afb7");
        softly.assertThat(getUser.response().statusCode()).as("Status code").isEqualTo(404);
        softly.assertThat(getUser.error().getError()).as("Validation error").isEqualTo("RESOURCE_NOT_FOUND");
        softly.assertAll();
    }

    @Test
    void verifyThatTheResponseBodyMatchesTheJsonSchema() {
        File userSchema = new File("src/main/java/schema/GetUserByIdSchema.json");
        GetUserList getUserList = new GetUserList(null, null, appId);
        String userId = getUserList.response().getBody().path("data[0].id");
        GetUser getUser = new GetUser(appId, userId);
        assertThat(getUser.response().getBody().asString(), JsonSchemaValidator.matchesJsonSchema(userSchema));
    }

}
