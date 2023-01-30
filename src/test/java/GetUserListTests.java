import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import api.GetUserList;

public class GetUserListTests {
    SoftAssertions softly = new SoftAssertions();
    GetUserList getUserList = new GetUserList();

    @Test
    void verifyThatASuccessfulRequestReturnsA200StatusCode() {
        softly.assertThat(getUserList.response().statusCode()).isEqualTo(200);
    }

    @Test
    void verifyThatTheApiImplementsPagination() {
        softly.assertThat(getUserList.response().getBody().path("limit").toString()).isEqualTo("20");
        softly.assertThat(getUserList.response().getBody().path("total").toString()).isNotEmpty();
        softly.assertThat(getUserList.response().getBody().path("page").toString()).isNotEmpty();
        softly.assertAll();
    }

}
