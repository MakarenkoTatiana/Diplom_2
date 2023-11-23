import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import model.Constants;
import model.User;
import org.junit.Test;

import static config.APIConfig.BASE_URI;
import static io.restassured.RestAssured.given;
import static model.Constants.*;

public class CreateUserTest extends TestBase{
    String correctAccessToken;
    String inCorrectAccessToken;
    /*@Override
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
    @Override
    public void tearDown() {
    }*/

    @DisplayName("Создание уникального пользователя")
    @Test
    public void registerUniqUser() {
        User uniqUser = new User(UPDT_EMAIL, UPDT_PASS, UPDT_USER_NAME);
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(uniqUser);
        response.then().assertThat()
                // статус ответа
                .statusCode(200);
        sendDeleteRequestAuthUser(accessToken);
    }

    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Test
    public void registerExistsUser() {
        User uniqUser = new User(UPDT_EMAIL, UPDT_PASS, UPDT_USER_NAME);
        // POST запрос на регистрацию
        sendPostRequestAuthRegisterWoToken(uniqUser);
        correctAccessToken = response.then().extract().path("accessToken");
        response.then()
                // статус ответа
                .statusCode(200);
        // POST запрос на регистрацию
        sendPostRequestAuthRegisterWoToken(uniqUser);
        response.then().assertThat()
                // статус ответа
                .statusCode(403);
        sendDeleteRequestAuthUser(correctAccessToken);
    }

    @DisplayName("Создание пользователя без заполнения одного из обязательных полей")
    @Test
    public void registerUserWithIncorrectData() {
        User incorrectUser = new User(EMAIL, PASS);
        // POST запрос на регистрацию
        sendPostRequestAuthRegisterWoToken(incorrectUser);
        response.then()
                // статус ответа
                .statusCode(403);
    }
}
