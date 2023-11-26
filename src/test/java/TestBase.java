import io.restassured.RestAssured;
import model.User;
import org.junit.After;
import org.junit.Before;

import static config.APIConfig.BASE_URI;
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
