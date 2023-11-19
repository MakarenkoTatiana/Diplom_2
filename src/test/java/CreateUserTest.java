import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTest extends TestBase{
    String accessToken;
    Response response;

    @Override
    public void setUp() {
        RestAssured.baseURI = Constants.baseUri;
    }
    @Override
    public void tearDown() {
    }

    @Test
    public void registerUniqUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.json);
        accessToken = response.then().extract().path("accessToken");
        response.then().assertThat()
                // статус ответа
                .statusCode(200);
        sendDeleteRequestAuthUser(accessToken);
    }

    @Test
    public void registerExistsUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.json);
        accessToken = response.then().extract().path("accessToken");
        response.then()
                // статус ответа
                .statusCode(200);
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.json);
        response.then().assertThat()
                // статус ответа
                .statusCode(403);
        sendDeleteRequestAuthUser(accessToken);
    }

    @Test
    public void registerUserWithIncorrectData() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.incorrectJson);
        response.then()
                // статус ответа
                .statusCode(403);
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
