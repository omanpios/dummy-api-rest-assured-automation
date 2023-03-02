package api.user;

import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.DeletedUser;
import pojo.Error;

public class DeleteUser {
    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public DeleteUser(String appId, String userId) {
        RequestSpecification request = given()
                .baseUri(baseUri)
                .header("app-id", appId);

        response = request.when()
                .delete("/user/" + userId)
                .then()
                .extract()
                .response();
    }

    public DeletedUser deleteResponse() {
        return response.getBody().as(DeletedUser.class);
    }

    public Response response() {
        return response;
    }

    public Error error() {
        return response.getBody().as(Error.class);
    }
}
