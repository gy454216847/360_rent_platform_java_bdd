package Pages;

import core.BrowserEmulator;
import core.knifeException;

public class LoginPage {
    public static BrowserEmulator driver;
    public static String url;
    public static String login_info = "xpath=>//*[@id=\"app\"]/div/div[2]/div[1]/section/h1 ";
    public static String login_error_info = "class=>info_wrap-p";
    public static String usernameBox = "class=>el-input__inner";
    public static String passwordBox = "xpath=>//*[@id=\"app\"]/div/div[2]/div/section[3]/div[2]/div/input";
    public static String loginBtn = "class=>btn_login";
    public static String Account_name = "xpath=>//*[@id=\"app\"]/div/div[2]/div[1]/section/div/span/div/p";
    public static String LogoutBtn = "class=>left_quit";
    public static String Logout_info = "xpath=>//*[@id=\"app\"]/div/div[2]/div/section[1]/div[2]/h6";

    public static void login_action(String username, String password) throws InterruptedException {
        driver.type(usernameBox, username);
        driver.type(passwordBox, password);
        driver.click(loginBtn);
        Thread.sleep(3000);

    }

    public static void logout_action() throws knifeException {
        driver.waitElement(Account_name, 5);
        driver.clickAndHold(Account_name);
        driver.waitElement(LogoutBtn, 5);
        driver.click(LogoutBtn);
        driver.waitElement(Logout_info, 5);
    }
}
