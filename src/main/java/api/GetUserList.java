package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import pojo.UserListResponse;

import static io.restassured.RestAssured.*;

public class GetUserList {
    Response response;
    private final String baseUri = "https://dummyapi.io/data/v1";

    public GetUserList(String page, String limit, String appId) {
        RequestSpecification request = given().log().all()
                .baseUri(baseUri)
                .header("app-id", appId);

        if (page != null) {
            request.queryParam("page", page);
        }
        if (limit != null) {
            request.queryParam("limit", limit);
        }
        response = request.when()
                .get("/user")
                .then().log().all()
                .extract()
                .response();
    }

    public UserListResponse userListResponse() {
        return response.getBody().as(UserListResponse.class);
    }

    public Response response() {
        return response;
    }
}
