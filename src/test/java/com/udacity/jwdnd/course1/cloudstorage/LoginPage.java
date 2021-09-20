package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement loginUsername;

    @FindBy(id = "inputPassword")
    private WebElement loginPassword;

    @FindBy(id = "login-submit-button")
    private WebElement loginButton;

    public void toLogin(WebDriver driver, int port, WebDriverWait wait)
    {
        driver.get("http://localhost:" + port + "/login");

        WebElement tempUsername = this.getLoginUsername();
        wait.until(ExpectedConditions.elementToBeClickable(tempUsername));
        //WebElement tempPass = loginPage.getLoginPassword();
        //wait.until(ExpectedConditions.elementToBeClickable(tempPass));
    }

    public void login(String username, String password)
    {
        getLoginUsername().sendKeys(username);
        getLoginPassword().sendKeys(password);

        getLoginButton().click();
    }


    public WebElement getLoginUsername() {
        return loginUsername;
    }

    public WebElement getLoginPassword() {
        return loginPassword;
    }

    public WebElement getLoginButton() {
        return loginButton;
    }
}
