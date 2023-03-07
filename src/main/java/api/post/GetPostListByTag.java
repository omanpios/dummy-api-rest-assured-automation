package api.post;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Error;
import pojo.post.PostListResponse;

import static io.restassured.RestAssured.given;

public class GetPostListByTag {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public GetPostListByTag(String appId, String tag) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json");

        response = request.when()
                .get("/tag/" + tag + "/post")
                .then()
                .extract()
                .response();
    }

    public PostListResponse postListResponse() {
        return response.getBody().as(PostListResponse.class);
    }

    public Response response() {
        return response;
    }

    public Error error() {
        return response.getBody().as(Error.class);
    }
}
