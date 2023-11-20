import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import static config.APIConfig.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class TestBase {
    public String accessToken;
    public Response response;
    public Response loginResponse;
    public Response updtResponse;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        sendPostRequestAuthRegister(Constants.JSON);
    }
    @After
    public void tearDown() {
        sendDeleteRequestAuthUser(accessToken);
    }

    @Step("Send POST request to /api/auth/register")
    public Response sendPostRequestAuthRegister(String jsonString) {
        response = given()
                .contentType(JSON)
                .body(jsonString)
                .when()
                .post(USER_CREATE_API);
        accessToken = response.then().extract().path("accessToken");
        return response;
    }

    @Step("Send DELETE request to /api/auth/user")
    public void sendDeleteRequestAuthUser(String token) {
        given()
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .delete(USER_MODIFIED_API);
    }

    @Step("Send POST request to /api/auth/logout")
    public void sendPostRequestAuthLogout(String refreshToken) {
        given()
                .contentType(JSON)
                .header("Authorization", refreshToken)
                .when()
                .post(USER_LOGOUT_API);
    }

    @Step("Send POST request to /api/auth/login")
    public Response sendPostRequestAuthLogin(String jsonString) {
        loginResponse = given()
                .contentType(JSON)
                .body(jsonString)
                .when()
                .post(USER_LOGIN_API);
        return loginResponse;
    }

    @Step("Send PATCH request to /api/auth/user")
    public void sendPatchRequestAuthUser(String token) {
        updtResponse = given()
                .contentType(JSON)
                .header("Authorization", token)
                .and()
                .body(Constants.UPDT_JSON)
                .patch(USER_MODIFIED_API);
    }
}
