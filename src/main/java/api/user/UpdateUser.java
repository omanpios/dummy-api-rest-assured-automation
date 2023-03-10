package api.user;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Error;
import pojo.user.User;

public class UpdateUser {
    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public UpdateUser(String appId, Object body, String userId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body);

        response = request.when()
                .put("/user/" + userId)
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
