import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.*;

public class UpdateUsersDataTest extends  TestBase{
    String refreshToken;
    String responseMessage;

    @Test
    public void updateDataWithAuthTest() {
        sendPostRequestAuthLogin(Constants.loginJson);
        refreshToken = loginResponse.then().extract().path("refreshToken");
        sendPatchRequestAuthUser(accessToken);
        String actualUserName = updtResponse
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.name");
        assertEquals(Constants.updtUserName, actualUserName);
        String actualEmail = updtResponse
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("user.email");
        assertEquals(Constants.updtEmail, actualEmail);
        sendPostRequestAuthLogout(refreshToken);
    }

    @Test
    public void updateDataWithoutAuthTest() {
        String emptyAccessToken = "";
        String expectedMessage = "You should be authorised";
        sendPatchRequestAuthUser(emptyAccessToken);
        responseMessage = updtResponse
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(expectedMessage, responseMessage);
    }

}
