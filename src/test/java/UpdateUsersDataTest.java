import io.qameta.allure.junit4.DisplayName;
import model.Constants;
import model.User;
import org.junit.Test;

import static model.Constants.*;
import static org.junit.Assert.assertEquals;
import static utils.AuthRequest.*;
import static utils.UserRequest.*;

public class UpdateUsersDataTest extends TestBase {
    String refreshToken;
    String responseMessage;

    @DisplayName("Изменение данных пользователя с авторизацией")
    @Test
    public void updateDataWithAuthTest() {
        User userLogin = new User(EMAIL, PASS);
        sendPostRequestAuthLogin(userLogin);
        refreshToken = loginResponse.then().extract().path("refreshToken");
        sendPatchRequestAuthUser(UPDT_EMAIL, UPDT_PASS, USER_NAME, accessToken);
        String actualUserName = updtResponse
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.name");
        assertEquals(Constants.UPDT_USER_NAME, actualUserName);
        String actualEmail = updtResponse
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.email");
        assertEquals(UPDT_EMAIL, actualEmail);
        sendPostRequestAuthLogout(refreshToken);
    }

    @DisplayName("Изменение данных пользователя без авторизации")
    @Test
    public void updateDataWithoutAuthTest() {
        String emptyAccessToken = "";
        String expectedMessage = "You should be authorised";
        sendPatchRequestAuthUser(UPDT_EMAIL, UPDT_PASS, USER_NAME, emptyAccessToken);
        responseMessage = updtResponse
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(expectedMessage, responseMessage);
    }

}
