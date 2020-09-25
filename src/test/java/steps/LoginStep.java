package steps;

import Pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStep {
    @Given("I am on the page")
    public void iAmOnThePage() throws InterruptedException {
        LoginPage.OpenPage();


    }

    @When("I input right {string} and right {string}")
    public void iInputRightAndRight(String username, String password) throws InterruptedException {
        LoginPage.driver.type(LoginPage.usernameBox, username);
        LoginPage.driver.type(LoginPage.passwordBox, password);


    }

    @And("I click login button")
    public void iClickLoginButton() throws InterruptedException {
        LoginPage.driver.click(LoginPage.loginBtn);
    }

    @Then("I can login the platform")
    public void iCanLoginThePlatform() throws InterruptedException {
        LoginPage.driver.waitElement(LoginPage.login_info, 5);
        assert LoginPage.driver.getElement(LoginPage.login_info).getText().equals("360租房运营平台");
    }

    @When("I input wrong {string} and wrong {string}")
    public void iInputWrongAndWrong(String username, String password) throws InterruptedException {
        LoginPage.driver.type(LoginPage.usernameBox,username);
        LoginPage.driver.type(LoginPage.passwordBox,password);
    }

    @Then("I can not login the platform")
    public void iCanNotLoginThePlatform() throws InterruptedException {
        LoginPage.driver.waitElement(LoginPage.login_error_info,5);
        assert LoginPage.driver.getElement(LoginPage.login_error_info).getText().equals("账号或密码错误，请重新输入");
    }

    @And("I click logout button")
    public void iClickLogoutButton() throws InterruptedException {
        LoginPage.driver.clickAndHold(LoginPage.Account_name);
        LoginPage.driver.click(LoginPage.LogoutBtn);

    }

    @Then("I can logout the platform")
    public void iCanLogoutThePlatform() throws InterruptedException{
        LoginPage.driver.waitElement(LoginPage.Logout_info,5);
       assert LoginPage.driver.getText(LoginPage.Logout_info).equals("360租房运营平台");
    }
}
