package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPage {
    @FindBy(id = "inputFirstName")
    private WebElement signupFirstName;

    @FindBy(id = "inputLastName")
    private WebElement signupLastName;

    @FindBy(name = "username")
    private WebElement signupUsername;

    @FindBy(id = "inputPassword")
    private WebElement signupPassword;

    @FindBy(id = "submit-signup-button")
    private WebElement signUpButton;

    @FindBy(id = "signUpError")
    private WebElement signUpError;

    public SignUpPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void toSignUp(int port, WebDriver driver, WebDriverWait wait)
    {
        driver.get("http://localhost:" + port + "/signup");
        wait.until(ExpectedConditions.elementToBeClickable(signupUsername));
    }

    public void signUp(String firstname, String lastname, String username, String password)
    {
        signupFirstName.sendKeys(firstname);
        signupLastName.sendKeys(lastname);
        signupUsername.sendKeys(username);
        signupPassword.sendKeys(password);
        signUpButton.click();
    }

    public WebElement getSignupFirstName() {
        return signupFirstName;
    }

    public WebElement getSignupLastName() {
        return signupLastName;
    }

    public WebElement getSignupUsername() {
        return signupUsername;
    }

    public WebElement getSignupPassword() {
        return signupPassword;
    }

    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public WebElement getSignUpError() {
        return signUpError;
    }
}
