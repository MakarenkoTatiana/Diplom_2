import model.Constants;
import model.User;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static model.Constants.*;

public class LoginUserTest extends TestBase {
    String refreshToken;

    @Test
    public void loginExistsUser() {
        User userLogin = new User(EMAIL, PASS);
        sendPostRequestAuthLogin(userLogin);
        refreshToken = loginResponse.then().extract().path("refreshToken");
        loginResponse.then().statusCode(200);
        sendPostRequestAuthLogout(refreshToken);
    }

    @Test
    public void loginIncorrectUser() {
        User userIncorrect = new User(EMAIL, INCORRECT_PASS);
        sendPostRequestAuthLogin(userIncorrect);
        loginResponse.then().statusCode(401);
    }
}
