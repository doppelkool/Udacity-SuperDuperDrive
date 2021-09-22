package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    @FindBy(id = "inputUsername")
    private WebElement loginUsername;

    @FindBy(id = "inputPassword")
    private WebElement loginPassword;

    @FindBy(id = "login-submit-button")
    private WebElement loginButton;

    @FindBy(id = "invalidLoginData")
    private WebElement invalidLoginData;

    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void toLogin(int port, WebDriver driver, WebDriverWait wait)
    {
        driver.get("http://localhost:" + port + "/login");

        WebElement tempUsername = this.getLoginUsername();
        wait.until(ExpectedConditions.elementToBeClickable(tempUsername));
    }

    public void login(String username, String password)
    {
        loginUsername.sendKeys(username);
        loginPassword.sendKeys(password);

        loginButton.click();
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

    public WebElement getInvalidLoginData()
    {
        return invalidLoginData;
    }
}
