package TestCase;

import core.knifeException;
import core.BrowserEmulator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class Login {
    BrowserEmulator driver;
    String url;

    String usernameBox = "class=>el-input__inner";
    String passwordBox = "xpath=>//*[@id=\"app\"]/div/div[2]/div/section[3]/div[2]/div/input";
    String loginBtn = "class=>btn_login";

    @BeforeMethod
    public void setUp() throws knifeException {

        url = "https://tc.lookdoor.cn:1999/";
        driver = new BrowserEmulator();
        driver.open(url);

    }

    @DataProvider(name = "Normal_Account")
    public Object[][] Keys(){
        return new Object[][]{
                {"admin","password"}

        };
    }

    @Test(dataProvider = "Normal_Account")
    public void Testcase1_Normal_Login(String username,String password) throws InterruptedException {
        driver.type(usernameBox, username);
        driver.type(passwordBox, password);
        driver.click(loginBtn);
        Thread.sleep(3000);

    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
        }

    }






