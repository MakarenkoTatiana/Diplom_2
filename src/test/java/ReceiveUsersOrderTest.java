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

public class ReceiveUsersOrderTest extends TestBase{
    int expectedOrders = 2;
    @DisplayName("Получение заказов авторизованного пользователя")
    @Test
    public void receiveAuthUsersOrder() {
        User userLogin = new User(EMAIL, PASS);
        sendPostRequestAuthLogin(userLogin);
        accessToken = loginResponse.then().extract().path("accessToken");
        prepareOrder();
        //Создаем несколько заказов
        sentPostRequestApiOrders(order);
        sentPostRequestApiOrders(order);
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
        //Создаем несколько заказов
        sentPostRequestApiOrders(order);
        sentPostRequestApiOrders(order);
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
