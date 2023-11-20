import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Constants;
import model.Order;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static config.APIConfig.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class CreateOrderTest extends TestBase{
    String accessToken;
    @Test
    public void createOrderWithAuthTest() {
        sendPostRequestAuthLogin(Constants.LOGIN_JSON);
        accessToken = loginResponse.then().extract().path("accessToken");
        List<String> ingredients = new ArrayList<>();
        String bun = getIngredientByNum(0);
        String stuffing = getIngredientByNum(1);
        String sauce = getIngredientByNum(4);
        ingredients.add(bun);
        ingredients.add(stuffing);
        ingredients.add(sauce);
        Order order = new Order(ingredients);

        Boolean result = given()
                .contentType(JSON)
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .post(ORDER_CREATE_API)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue())
                .extract()
                .path("success");
        assertTrue(result);
    }
    @Test
    public void createOrderWithoutAuthTest() {
        List<String> ingredients = new ArrayList<>();
        String bun = getIngredientByNum(0);
        String stuffing = getIngredientByNum(1);
        String sauce = getIngredientByNum(4);
        ingredients.add(bun);
        ingredients.add(stuffing);
        ingredients.add(sauce);
        Order order = new Order(ingredients);

        Boolean result = given()
                .contentType(JSON)
                .body(order)
                .post(ORDER_CREATE_API)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("order.number", notNullValue())
                .extract()
                .path("success");
        assertTrue(result);
    }
    @Test
    public void createOrderWithoutIngredientsTest() {
        String expectedMess = "Ingredient ids must be provided";
        List<String> ingredients = new ArrayList<>();
        Order order = new Order(ingredients);
        String actualMess = given()
                .contentType(JSON)
                .body(order)
                .post(ORDER_CREATE_API)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
        assertEquals(expectedMess, actualMess);
    }
    @Test
    public void createOrderWithIncorrectHashTest() {
        String incorrectHash = "incorrectHash";
        List<String> ingredients = new ArrayList<>();
        ingredients.add(incorrectHash);
        Order order = new Order(ingredients);
        given()
                .contentType(JSON)
                .body(order)
                .post(ORDER_CREATE_API)
                .then()
                .assertThat()
                .statusCode(500);
    }
    @Step("Get Ingredients List")
    public Response getIngredientsList () {
        Response ingredientsResponse = given()
                .contentType(JSON)
                .when()
                .get(INGREDIENTS_GET_API);
        return ingredientsResponse;
    }
    @Step("Get Ingredient By Number")
    public String getIngredientByNum (int ingredientNum) {
        return getIngredientsList().
                then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path(String.format("data[%s]._id", ingredientNum));
    }
}
