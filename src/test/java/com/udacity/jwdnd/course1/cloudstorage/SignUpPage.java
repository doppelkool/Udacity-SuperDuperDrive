package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    private WebElement signupFirstName;

    @FindBy(id = "inputLastName")
    private WebElement signupLastName;

    @FindBy(id = "inputUsername")
    private WebElement signupUsername;

    @FindBy(id = "inputPassword")
    private WebElement signupPassword;

    @FindBy(id = "submit-signup-button")
    private WebElement signUpButton;

    public void signUp(String firstname, String lastname, String username, String password)
    {
        getSignupFirstName().sendKeys(firstname);
        getSignupLastName().sendKeys(lastname);
        getSignupUsername().sendKeys(username);
        getSignupPassword().sendKeys(password);
        getSignUpButton().click();
    }

    public WebElement getSignupFirstName() {
        return signupFirstName;
    }

    public void setSignupFirstName(WebElement signupFirstName) {
        this.signupFirstName = signupFirstName;
    }

    public WebElement getSignupLastName() {
        return signupLastName;
    }

    public void setSignupLastName(WebElement signupLastName) {
        this.signupLastName = signupLastName;
    }

    public WebElement getSignupUsername() {
        return signupUsername;
    }

    public void setSignupUsername(WebElement signupUsername) {
        this.signupUsername = signupUsername;
    }

    public WebElement getSignupPassword() {
        return signupPassword;
    }

    public void setSignupPassword(WebElement signupPassword) {
        this.signupPassword = signupPassword;
    }

    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(WebElement signUpButton) {
        this.signUpButton = signUpButton;
    }
}
