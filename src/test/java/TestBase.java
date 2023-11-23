import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Constants;
import model.Order;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.internal.requests.OrderingRequest;

import java.util.ArrayList;
import java.util.List;

import static config.APIConfig.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static model.Constants.*;

public class TestBase {
    public String accessToken;
    public Response response;
    public Response loginResponse;
    public Response updtResponse;
    public Response orderResponse;
    public Order order;
    User user;

    @Before
    public void setUp() {
        user = new User(EMAIL, PASS, USER_NAME);
        RestAssured.baseURI = BASE_URI;
        sendPostRequestAuthRegister(user);
    }
    @After
    public void tearDown() {
        sendDeleteRequestAuthUser(accessToken);
    }

    @Step("Send POST request to /api/auth/register")
    public Response sendPostRequestAuthRegister(Object object) {
        response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_CREATE_API);
        accessToken = response.then().extract().path("accessToken");
        return response;
    }

    @Step("Send POST request to /api/auth/register without accessToken")
    public Response sendPostRequestAuthRegisterWoToken(Object object) {
        response = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_CREATE_API);
        return response;
    }

    @Step("Send DELETE request to /api/auth/user")
    public void sendDeleteRequestAuthUser(String token) {
        given()
                .contentType(JSON)
                .header("Authorization", token)
                .when()
                .delete(USER_MODIFIED_API);
    }

    @Step("Send POST request to /api/auth/logout")
    public void sendPostRequestAuthLogout(String refreshToken) {
        given()
                .contentType(JSON)
                .header("Authorization", refreshToken)
                .when()
                .post(USER_LOGOUT_API);
    }

    @Step("Send POST request to /api/auth/login")
    public Response sendPostRequestAuthLogin(Object object) {
        loginResponse = given()
                .contentType(JSON)
                .body(object)
                .when()
                .post(USER_LOGIN_API);
        return loginResponse;
    }

    @Step("Send PATCH request to /api/auth/user")
    public void sendPatchRequestAuthUser(String token) {
        updtResponse = given()
                .contentType(JSON)
                .header("Authorization", token)
                .and()
                .body(Constants.UPDT_JSON)
                .patch(USER_MODIFIED_API);
    }

    @Step("Send POST request to /api/orders")
    public Response sentPostRequestApiOrders(Order ord) {
        orderResponse = given()
                .contentType(JSON)
                .header("Authorization", accessToken)
                .and()
                .body(ord)
                .post(ORDER_CREATE_API);
        return orderResponse;
    }

    @Step("Send POST request to /api/orders without auth")
    public Response sentPostRequestApiOrdersWithoutAuth(Order ord) {
        orderResponse = given()
                .contentType(JSON)
                .body(ord)
                .post(ORDER_CREATE_API);
        return orderResponse;
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
    @Step("Prepare order")
    public Order prepareOrder() {
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
}
