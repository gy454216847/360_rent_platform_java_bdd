package steps;

import Pages.LoginPage;
import io.cucumber.java.After;

public class MyUnitSteps {
  @After
    public void tearDown() throws Exception {

      LoginPage.driver.sendEmail();

    //BrowserEmulator.browser.close();

  }


  }

