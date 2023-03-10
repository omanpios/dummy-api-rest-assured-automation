package api.user;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.user.User;

public class CreateUser {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public CreateUser(String appId, Object body) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId)
                .header("Content-Type", "application/json")
                .body(body);

        response = request.when()
                .post("/user/create")
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
