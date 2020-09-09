package TestCase;

import Pages.LoginPage;
import core.BrowserEmulator;
import core.knifeException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class myunit extends LoginPage {

    @BeforeMethod
    public void setUp() throws knifeException {

        url = "https://tc.lookdoor.cn:1999/";
        driver = new BrowserEmulator();
        driver.open(url);

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
