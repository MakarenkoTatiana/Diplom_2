import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.Order;
import model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Constants.EMAIL;
import static model.Constants.PASS;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static utils.AuthRequest.loginResponse;
import static utils.AuthRequest.sendPostRequestAuthLogin;
import static utils.OrderRequest.*;

public class CreateOrderTest extends TestBase {
    String accessToken;
    Response orderResponse;

    @DisplayName("Создание заказа с авторизацией")
    @Test
    public void createOrderWithAuthTest() {
        User userLogin = new User(EMAIL, PASS);
        sendPostRequestAuthLogin(userLogin);
        accessToken = loginResponse.then().extract().path("accessToken");
        prepareOrder();
        Boolean result = sentPostRequestApiOrders(order, accessToken)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue())
                .extract()
                .path("success");
        assertTrue(result);
    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    public void createOrderWithoutAuthTest() {
        prepareOrder();
        Boolean result = sentPostRequestApiOrdersWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue())
                .extract()
                .path("success");
        assertTrue(result);
    }

    @DisplayName("Создание заказа без ингредиентов")
    @Test
    public void createOrderWithoutIngredientsTest() {
        String expectedMess = "Ingredient ids must be provided";
        List<String> ingredients = new ArrayList<>();
        Order order = new Order(ingredients);
        String actualMess = sentPostRequestApiOrdersWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertEquals(expectedMess, actualMess);
    }

    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Test
    public void createOrderWithIncorrectHashTest() {
        String incorrectHash = "incorrectHash";
        List<String> ingredients = new ArrayList<>();
        ingredients.add(incorrectHash);
        Order order = new Order(ingredients);
        sentPostRequestApiOrdersWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(500);
    }
}
