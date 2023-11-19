import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class CreateUserTest extends TestBase{
    String accessToken1;
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
        response.then().assertThat()
                // статус ответа
                .statusCode(200);
        sendDeleteRequestAuthUser(accessToken);
    }

    @Test
    public void registerExistsUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.json);
        accessToken1 = response.then().extract().path("accessToken");
        response.then()
                // статус ответа
                .statusCode(200);
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.json);
        response.then().assertThat()
                // статус ответа
                .statusCode(403);
        sendDeleteRequestAuthUser(accessToken1);
    }

    @Test
    public void registerUserWithIncorrectData() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.incorrectJson);
        response.then()
                // статус ответа
                .statusCode(403);
    }
}
