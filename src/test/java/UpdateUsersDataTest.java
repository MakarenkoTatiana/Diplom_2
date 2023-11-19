import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.*;

public class UpdateUsersDataTest extends  TestBase{
    String refreshToken;
    Response updtResponse;
    String responseMessage;

    @Test
    public void updateDataWithAuthTest() {
        sendPostRequestAuthLogin(Constants.loginJson);
        refreshToken = loginResponse.then().extract().path("refreshToken");
        updtResponse = given()
                .contentType(JSON)
                .header("Authorization", accessToken)
                .and()
                .body(Constants.UPDT_JSON)
                .patch("/api/auth/user");
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
        updtResponse = given()
                .contentType(JSON)
                .header("Authorization", emptyAccessToken)
                .and()
                .body(Constants.UPDT_JSON)
                .patch("/api/auth/user");
        responseMessage = updtResponse
                .then()
                .assertThat()
                .statusCode(401)
                .extract()
                .path("message");
        assertEquals(expectedMessage, responseMessage);
    }

}
