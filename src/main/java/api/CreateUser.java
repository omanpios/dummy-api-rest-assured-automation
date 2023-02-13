package api;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.User;

public class CreateUser {

    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public CreateUser(String appId) {
        RequestSpecification request = given().log().all()
                .baseUri(baseUri)
                .header("app-id", appId);

        response = when()
                .post("/user/create")
                .then().log().all()
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
