import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import api.GetUserList;
import io.restassured.module.jsv.JsonSchemaValidator;

public class GetUserListTests {
    SoftAssertions softly = new SoftAssertions();
    String appId = "63d1caa3480870720570afb7";

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        GetUserList getUserList = new GetUserList(null, null, appId);
        softly.assertThat(getUserList.response().statusCode()).as("Status code").isEqualTo(200);
        softly.assertAll();
    }

    @Test
    void verifyThatTheApiImplementsPagination() {
        GetUserList getUserList = new GetUserList(null, null, appId);
        softly.assertThat(getUserList.userResponse().getLimit()).as("Limit").isEqualTo(20);
        softly.assertThat(getUserList.userResponse().getTotal()).as("Total").isGreaterThan(100);
        softly.assertThat(getUserList.userResponse().getPage()).as("Page").isEqualTo(0);
        softly.assertAll();
    }

    @Test
    void verifyThatAnInvalidAuthenticationReturnsA403StatusCode() {
        File errorMessageSchema = new File("src/main/java/schema/ErrorMessageSchema.json");
        GetUserList getUserList = new GetUserList(null, null, "63d1caa34808707205700000");
        softly.assertThat(getUserList.response().statusCode()).as("Status code").isEqualTo(403);
        softly.assertAll();
        assertThat(getUserList.response().getBody().asString(), JsonSchemaValidator.matchesJsonSchema(errorMessageSchema));
    }

    @Test
    void verifyThatTheResponseBodyMatchesTheJsonSchema() {
        File userListSchema = new File("src/main/java/schema/GetListUserSchema.json");
        GetUserList getUserList = new GetUserList(null, null, appId);
        softly.assertThat(getUserList.response().statusCode()).as("Status code").isEqualTo(200);
        softly.assertAll();
        assertThat(getUserList.response().getBody().asString(), JsonSchemaValidator.matchesJsonSchema(userListSchema));
    }
}
