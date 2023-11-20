import model.Constants;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class LoginUserTest extends TestBase {
    String refreshToken;

    @Test
    public void loginExistsUser() {
        sendPostRequestAuthLogin(Constants.LOGIN_JSON);
        refreshToken = loginResponse.then().extract().path("refreshToken");
        loginResponse.then().statusCode(200);
        sendPostRequestAuthLogout(refreshToken);
    }

    @Test
    public void loginIncorrectUser() {
        sendPostRequestAuthLogin(Constants.INCORRECT_LOGIN_JSON);
        loginResponse.then().statusCode(401);
    }
}
