package api.post;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.post.Posts;

import static io.restassured.RestAssured.given;

public class UpdatePost {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public UpdatePost(String appId, Object body, String postId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body);

        response = request.when()
                .put("/post/" + postId)
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
