package post;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import api.post.CreatePost;
import api.user.GetUserList;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import pojo.post.PostRequest;

public class CreatePostTests {

    Faker fake = new Faker();
    SoftAssertions softly = new SoftAssertions();
    String appId = System.getenv("APP_ID");

    @BeforeAll
    static void log() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter(), new ErrorLoggingFilter());
    }

    @Test
    void verifyThatASuccessfulRequestBasicDataReturnsA200StatusCode() {
        PostRequest postData = new PostRequest();
        postData.setText(fake.elderScrolls().dragon());
        postData.setImage(fake.internet().image());
        postData.setLikes(fake.number().numberBetween(0, 10));

        GetUserList getUserList = new GetUserList(null, null, appId);
        String userId = getUserList.response().getBody().path("data[0].id");

        postData.setOwner(userId);

        CreatePost createPost = new CreatePost(appId, postData);
        softly.assertThat(createPost.response().getStatusCode()).as("Status code").isEqualTo(200);
        softly.assertAll();
    }
}
