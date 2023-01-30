package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class GetUserList {
    Response response;

    public GetUserList() {
        response = given()
                .header("app-id", "63d1caa3480870720570afb7")
                .get("https://dummyapi.io/data/v1/user");
    }

    public String response() {
        String responseBody = response.asPrettyString();
        return responseBody;
    }
}
