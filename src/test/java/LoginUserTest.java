import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginUserTest {
    String accessToken;
    Response response;
    @Before
    public void setUp() {
        RestAssured.baseURI = Constants.baseUri;
        sendPostRequestAuthRegister(Constants.json);
        accessToken = response.then().extract().path("accessToken");
    }

    @After
    public void tearDown() {
        sendDeleteRequestAuthUser(accessToken);
    }

    @Test
    public void loginExistsUser() {
    Response loginResponse = (Response) given()
                .header("Content-type", "application/json")
                .body(Constants.loginJson)
                .when()
                .post("/api/auth/login");
    String refreshToken = loginResponse.then().extract().path("refreshToken");
    loginResponse.then()
                // статус ответа
                .statusCode(200);
        given()
                .header("Content-type", "application/json")
                .header("Authorization", refreshToken)
                .when()
                .post("/api/auth/logout");
    }

    @Test
    public void loginIncorrectUser() {
        given()
                .header("Content-type", "application/json")
                .body(Constants.incorrectLoginJson)
                .when()
                .post("/api/auth/login")
                .then()
                // статус ответа
                .statusCode(401);
    }
    @Step("Send POST request to /api/auth/register")
    public Response sendPostRequestAuthRegister(String jsonString) {
        response = given()
                .header("Content-type", "application/json")
                .body(jsonString)
                .when()
                .post("/api/auth/register");
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
}
