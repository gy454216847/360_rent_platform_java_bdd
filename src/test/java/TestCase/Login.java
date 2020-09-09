package TestCase;


import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Feature("登录测试")
public class Login extends Myunit {


    @DataProvider(name = "Normal_Account")
    public Object[][] Keys() {
        return new Object[][]{
                {"admin", "password"}

        };
    }

    @DataProvider(name = "Wrong_Account")
    public Object[][] WrongKeys() {
        return new Object[][]{
                {"admin", "123456"}
        };
    }

    @Test(dataProvider = "Normal_Account",description = "正常登陆")
    public void TestCase1_Normal_Login(String username, String password) throws InterruptedException {
        login_action(username, password);
        assertEquals(driver.getText(login_info), "360租房运营平台");

    }

    @Test(dataProvider = "Wrong_Account",description = "错误登陆")
    public void TestCase2_Wrong_Login(String username, String password) throws InterruptedException {
        login_action(username, password);
        assertEquals(driver.getText(login_error_info), "账号或密码错误，请重新输入");

    }

    @Test(dataProvider = "Normal_Account",description = "登出测试")
    public void TestCase3_logOut(String username, String password) throws InterruptedException {
        login_action(username, password);
        assertEquals(driver.getText(login_info), "360租房运营平台");
        logout_action();
        assertEquals(driver.getText(Logout_info), "360租房运营平台");


    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}






