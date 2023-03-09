package api.post;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.post.Posts;

import static io.restassured.RestAssured.given;

public class DeletePost {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public DeletePost(String appId, Object body, String postId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body);

        response = request.when()
                .delete("/post/" + postId)
                .then()
                .extract()
                .response();
    }

    public DeletePost postResponse() {
        return response.getBody().as(DeletePost.class);
    }

    public Response response() {
        return response;
    }

    public Error error() {
        return response.getBody().as(Error.class);
    }
}
