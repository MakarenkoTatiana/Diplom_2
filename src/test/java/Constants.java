public class Constants {
    public static final String email = "makar_test@gmail.ru";
    public static final String pass = "MakarTest12345";
    public static final String incorrectPass = "Test12345";
    public static final String userName = "MakarTestUser";

    public static final String updtEmail = "updt_test@gmail.ru";
    public static final String updtPass = "updtTest12345";
    public static final String updtUserName = "updtTestUser";

    public static final String baseUri = "https://stellarburgers.nomoreparties.site";
    public static final String json = "{\"email\": \"" + email + "\", \"password\": \"" + pass + "\", \"name\": \"" + userName + "\"}";
    public static final String incorrectJson = "{\"email\": \"" + email + "\", \"password\": \"" + pass + "\", \"name\": \"\"}";
    public static final String incorrectLoginJson = "{\"email\": \"" + email + "\", \"password\": \"" + incorrectPass + "\", \"name\": \"\"}";
    public static final String loginJson = "{\"email\": \"" + email + "\", \"password\": \"" + pass + "\"}";

    public static final String UPDT_JSON = "{\"email\": \"" + updtEmail + "\", \"password\": \"" + updtPass + "\", \"name\": \"" + updtUserName + "\"}";

}
