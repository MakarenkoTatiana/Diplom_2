package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Order;

import java.util.ArrayList;
import java.util.List;

import static config.APIConfig.INGREDIENTS_GET_API;
import static config.APIConfig.ORDER_CREATE_API;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderRequest {
    public static Response orderResponse;
    public static Order order;

    @Step("Send POST request to /api/orders")
    public static Response sentPostRequestApiOrders(Order testOrder, String token) {
        orderResponse = given()
                .contentType(JSON)
                .header("Authorization", token)
                .and()
                .body(testOrder)
                .post(ORDER_CREATE_API);
        return orderResponse;
    }

    @Step("Send POST request to /api/orders without auth")
    public static Response sentPostRequestApiOrdersWithoutAuth(Order testOrder) {
        orderResponse = given()
                .contentType(JSON)
                .body(testOrder)
                .post(ORDER_CREATE_API);
        return orderResponse;
    }

    @Step("Prepare order")
    public static Order prepareOrder() {
        List<String> ingredients = new ArrayList<>();
        String bun = getIngredientByNum(0);
        String stuffing = getIngredientByNum(1);
        String sauce = getIngredientByNum(4);
        ingredients.add(bun);
        ingredients.add(stuffing);
        ingredients.add(sauce);
        order = new Order(ingredients);
        return order;
    }
    @Step("Get Ingredients List")
    public static Response getIngredientsList () {
        Response ingredientsResponse = given()
                .contentType(JSON)
                .when()
                .get(INGREDIENTS_GET_API);
        return ingredientsResponse;
    }
    @Step("Get Ingredient By Number")
    public static String getIngredientByNum (int ingredientNum) {
        return getIngredientsList().
                then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path(String.format("data[%s]._id", ingredientNum));
    }
}
