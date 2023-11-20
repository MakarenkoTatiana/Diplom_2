package model;

public class Constants {
    public static final String EMAIL = "makar_test@gmail.ru";
    public static final String PASS = "MakarTest12345";
    public static final String INCORRECT_PASS = "Test12345";
    public static final String USER_NAME = "MakarTestUser";
    public static final String UPDT_EMAIL = "updt_test@gmail.ru";
    public static final String UPDT_PASS = "updtTest12345";
    public static final String UPDT_USER_NAME = "updtTestUser";
    public static final String JSON = "{\"email\": \"" + EMAIL + "\", \"password\": \"" + PASS + "\", \"name\": \"" + USER_NAME + "\"}";
    public static final String INCORRECT_JSON = "{\"email\": \"" + EMAIL + "\", \"password\": \"" + PASS + "\", \"name\": \"\"}";
    public static final String INCORRECT_LOGIN_JSON = "{\"email\": \"" + EMAIL + "\", \"password\": \"" + INCORRECT_PASS + "\", \"name\": \"\"}";
    public static final String LOGIN_JSON = "{\"email\": \"" + EMAIL + "\", \"password\": \"" + PASS + "\"}";
    public static final String UPDT_JSON = "{\"email\": \"" + UPDT_EMAIL + "\", \"password\": \"" + UPDT_PASS + "\", \"name\": \"" + UPDT_USER_NAME + "\"}";

}
