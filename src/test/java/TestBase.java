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
import static utils.UserRequest.*;

public class TestBase {
    private User user;

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

}
