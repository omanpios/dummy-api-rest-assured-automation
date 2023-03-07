package api.post;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Error;
import pojo.post.PostListResponse;
import pojo.post.Posts;

import static io.restassured.RestAssured.given;

public class GetPostById {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public GetPostById(String appId, String postId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json");

        response = request.when()
                .get("/post/" + postId)
                .then()
                .extract()
                .response();
    }

    public Posts postResponse() {
        return response.getBody().as(Posts.class);
    }

    public Response response() {
        return response;
    }

    public Error error() {
        return response.getBody().as(Error.class);
    }
}
