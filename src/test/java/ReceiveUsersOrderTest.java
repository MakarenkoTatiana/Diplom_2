import io.qameta.allure.junit4.DisplayName;
import model.Constants;
import model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static config.APIConfig.ORDER_GET_API;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static model.Constants.EMAIL;
import static model.Constants.PASS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static utils.AuthRequest.loginResponse;
import static utils.AuthRequest.sendPostRequestAuthLogin;
import static utils.OrderRequest.*;

public class ReceiveUsersOrderTest extends TestBase{
    private String accessToken;
    private String emptyAccessToken;
    private int expectedOrders = 2;
    @DisplayName("Получение заказов авторизованного пользователя")
    @Test
    public void receiveAuthUsersOrder() {
        User userLogin = new User(EMAIL, PASS);
        sendPostRequestAuthLogin(userLogin);
        accessToken = loginResponse.then().extract().path("accessToken");
        prepareOrder();
        //Создаем несколько заказов
        sentPostRequestApiOrders(prepareOrder(),accessToken);
        sentPostRequestApiOrders(prepareOrder(), accessToken);
        List<String> AuthUsersOrders = given()
                .header("Authorization", accessToken)
                .get(ORDER_GET_API)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .extract()
                .jsonPath().getList("orders._id");
        assertEquals(expectedOrders, AuthUsersOrders.size());
    }

    @DisplayName("Получение заказов неавторизованного пользователя")
    @Test
    public void receiveWithoutAuthUsersOrder() {
        String expectedMess = "You should be authorised";
        prepareOrder();
        sentPostRequestApiOrdersWithoutAuth(prepareOrder());
        given()
                .contentType(JSON)
                .get(ORDER_GET_API)
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

}
