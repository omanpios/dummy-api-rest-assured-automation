package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class GetUserList {
    Response response;

    public GetUserList(Object page, Object limit) {
        response = given()
                .header("app-id", "63d1caa3480870720570afb7")
                .queryParam("page", page = page != null ? page : 0)
                .queryParam("limit", limit = limit != null ? limit : 0)
                .log().all()
                .get("https://dummyapi.io/data/v1/user");
    }

    public Response response() {
        return response;
    }
}
