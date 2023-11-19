import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static io.restassured.RestAssured.given;

public class TestBase {
    public String accessToken;
    public Response response;
    public Response loginResponse;

    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseUri;
        sendPostRequestAuthRegister(Constants.json);
    }
    @After
    public void tearDown() {
        sendDeleteRequestAuthUser(accessToken);
    }

    @Step("Send POST request to /api/auth/register")
    public Response sendPostRequestAuthRegister(String jsonString) {
        response = given()
                .header("Content-type", "application/json")
                .body(jsonString)
                .when()
                .post("/api/auth/register");
        accessToken = response.then().extract().path("accessToken");
        return response;
    }

    @Step("Send DELETE request to /api/auth/user")
    public void sendDeleteRequestAuthUser(String token) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .when()
                .delete("/api/auth/user");
    }

    @Step("Send POST request to /api/auth/logout")
    public void sendPostRequestAuthLogout(String refreshToken) {
        given()
                .header("Content-type", "application/json")
                .header("Authorization", refreshToken)
                .when()
                .delete("/api/auth/user");
    }

    @Step("Send POST request to /api/auth/login")
    public Response sendPostRequestAuthLogin(String jsonString) {
        loginResponse = given()
                .header("Content-type", "application/json")
                .body(jsonString)
                .when()
                .post("/api/auth/login");
        return loginResponse;
    }
}
