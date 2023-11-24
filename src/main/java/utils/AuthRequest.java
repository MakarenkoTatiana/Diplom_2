package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static config.APIConfig.USER_LOGIN_API;
import static config.APIConfig.USER_LOGOUT_API;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class AuthRequest {
    public static Response loginResponse;
    @Step("Send POST request to /api/auth/logout")
    public static void sendPostRequestAuthLogout(String refreshToken) {
        given()
                .contentType(JSON)
                .header("Authorization", refreshToken)
                .when()
                .post(USER_LOGOUT_API);
    }

    @Step("Send POST request to /api/auth/login")
    public static Response sendPostRequestAuthLogin(Object object) {
        loginResponse = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_LOGIN_API);
        return loginResponse;
    }
}
