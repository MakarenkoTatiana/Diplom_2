import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.Constants;
import org.junit.Test;

import static config.APIConfig.BASE_URI;
import static io.restassured.RestAssured.given;

public class CreateUserTest extends TestBase{
    String correctAccessToken;
    @Override
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
    @Override
    public void tearDown() {
    }

    @DisplayName("Создание уникального пользователя")
    @Test
    public void registerUniqUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        response.then().assertThat()
                // статус ответа
                .statusCode(200);
        sendDeleteRequestAuthUser(accessToken);
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
    public void registerExistsUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        correctAccessToken = response.then().extract().path("accessToken");
        response.then()
                // статус ответа
                .statusCode(200);
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        response.then().assertThat()
                // статус ответа
                .statusCode(403);
        sendDeleteRequestAuthUser(correctAccessToken);
    }

    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Test
    public void registerUserWithIncorrectData() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.INCORRECT_JSON);
        response.then()
                // статус ответа
                .statusCode(403);
    }
}
