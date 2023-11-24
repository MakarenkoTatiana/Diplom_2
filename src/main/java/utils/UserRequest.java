package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;

import static config.APIConfig.USER_CREATE_API;
import static config.APIConfig.USER_MODIFIED_API;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static model.Constants.*;

public class UserRequest {
    public static String accessToken;
    public static Response response;
    public static Response updtResponse;
    private static User updtUser;
    @Step("Send POST request to /api/auth/register")
    public static Response sendPostRequestAuthRegister(Object object) {
        response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_CREATE_API);
        accessToken = response.then().extract().path("accessToken");
        return response;
    }

    @Step("Send POST request to /api/auth/register without accessToken")
    public static Response sendPostRequestAuthRegisterWoToken(Object object) {
        response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_CREATE_API);
        return response;
    }

    @Step("Send DELETE request to /api/auth/user")
    public static void sendDeleteRequestAuthUser(String token) {
        given()
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .delete(USER_MODIFIED_API);
    }

    @Step("Send PATCH request to /api/auth/user")
    public static void sendPatchRequestAuthUser(String token) {
        updtUser = new User(UPDT_EMAIL, UPDT_PASS, USER_NAME);
        updtResponse = given()
                .contentType(JSON)
                .header("Authorization", token)
                .and()
                .body(updtUser)
                .patch(USER_MODIFIED_API);
    }
}
