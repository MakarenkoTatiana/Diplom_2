import io.restassured.RestAssured;
import model.Constants;
import org.junit.Test;

import static config.APIConfig.BASE_URI;
import static io.restassured.RestAssured.given;

public class CreateUserTest extends TestBase{
    String accessToken1;
    @Override
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }
    @Override
    public void tearDown() {
    }

    @Test
    public void registerUniqUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        response.then().assertThat()
                // статус ответа
                .statusCode(200);
        sendDeleteRequestAuthUser(accessToken);
    }

    @Test
    public void registerExistsUser() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        accessToken1 = response.then().extract().path("accessToken");
        response.then()
                // статус ответа
                .statusCode(200);
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.JSON);
        response.then().assertThat()
                // статус ответа
                .statusCode(403);
        sendDeleteRequestAuthUser(accessToken1);
    }

    @Test
    public void registerUserWithIncorrectData() {
        // POST запрос на регистрацию
        sendPostRequestAuthRegister(Constants.INCORRECT_JSON);
        response.then()
                // статус ответа
                .statusCode(403);
    }
}
