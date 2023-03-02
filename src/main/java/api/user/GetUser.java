package api.user;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Error;
import pojo.user.User;

import static io.restassured.RestAssured.given;

public class GetUser {
    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public GetUser(String appId, String userId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId);

        response = request.when()
                .get("/user/" + userId)
                .then()
                .extract()
                .response();
    }

    public User userResponse() {
        return response.getBody().as(User.class);
    }

    public Response response() {
        return response;
    }

    public Error error() {
        return response.getBody().as(Error.class);
    }
}
