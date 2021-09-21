package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class HomePage {

    public HomePage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

    public void toHome(WebDriver driver)
    {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(logoutButton));
    }

    //region Note-Stuff
    public void navNotesTab(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", notes_tab);
    }

    //region Note-Create
    public void navNewNote(WebDriver driver)
    {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
    }

    public void createNote(WebDriver driver, String newNoteTitle, String newNoteDesc) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(note_title_input)).sendKeys(newNoteTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(note_desc_input)).sendKeys(newNoteDesc);
        newNote_Submit.click();
    }
    //endregion

    //region Note-Edit
    public void navEditNote(WebDriver driver) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(note_edit_button)).click();
    }

    public void editNote(WebDriver driver, String newTitle, String newDesc) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editNote_title)).clear();
        editNote_title.sendKeys(newTitle);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editNote_description)).clear();
        editNote_description.sendKeys(newDesc);
        edit_note_button.click();
    }
    //endregion

    public void deleteNote(WebDriver driver) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(note_delete_button)).click();
    }

    public boolean checkForNote(String noteTitleText) {
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        boolean created = false;
        for (WebElement element : notesList) {
            if (element.getAttribute("innerHTML").equals(noteTitleText)) {
                created = true;
                break;
            }
        }
        return created;
    }

    public int getNoteCount() {
        List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
        return notesList.size();
    }
    //endregion

    //region Credential-Stuff
    public void navCredTab(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", creds_tab);
    }

    //region Credential-Create
    public void navNewCred(WebDriver driver) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(newCredButton)).click();
    }

    public void createCred(WebDriver driver, String newCredUrl, String newCredUser, String newCredPW) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(cred_url_input)).sendKeys(newCredUrl);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(cred_username_input)).sendKeys(newCredUser);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(cred_password_input)).sendKeys(newCredPW);
        cred_input_submit.click();
    }
    //endregion

    //region Credential-Edit
    public String getCredPW() {
        return cred_password_input.getText();
    }

    public void navEditCred(WebDriver driver) {
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(editCredButton)).click();
    }

    public void editCred(WebDriver driver, String newCredUrl, String newCredUser, String newCredPW) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredUrl)).clear();
        editCredUrl.sendKeys(newCredUrl);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredUsername)).clear();
        editCredUsername.sendKeys(newCredUser);
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(editCredPassword)).clear();
        editCredPassword.sendKeys(newCredPW);
        editCredSave.click();
    }
    //endregion

    public void deleteCred(WebDriver driver) throws InterruptedException {
        Thread.sleep(800);

        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(deleteNoteBtn)).click();
    }

    public boolean checkForCred(String credUrl, String credUser) {
        List<WebElement> notesList = credsTable.findElements(By.tagName("th"));
        boolean created = false;
        for (WebElement element : notesList) {
            if (element.getAttribute("innerHTML").equals(credUrl)) {
                created = true;
                break;
            }
        }
        return created;
    }

    public int getCredCount() {
        List<WebElement> credList = credsTable.findElements(By.tagName("th"));
        return credList.size();
    }

    public boolean checkForCredentialEncryptedPassword(String url, String username, CredentialService credentialService) {
        return getCredentialTableRowAlternative(url, username, credentialService) != null;
    }

    private WebElement getCredentialTableRowAlternative(String url, String username, CredentialService credentialService) {
        WebElement cerdentialRow = null;
        try {
            WebElement body = credsTable.findElement(By.tagName("tbody"));
            if (body != null) {
                List<WebElement> rows = body.findElements(By.tagName("tr"));
                if (rows != null && !rows.isEmpty()) {
                    int x = -1;
                    for (int i = 0; i < credentialService.getMaxIDFromCred(); i++) {
                        x++;
                        Credential credential = credentialService.getCredentialByID(i+1);
                        if(credential == null) continue;

                        WebElement row = rows.get(x);
                        WebElement textCredentialUrl = row.findElement(By.id("credential-table-url"));
                        WebElement textCredentialUsername = row.findElement(By.id("credential-table-username"));
                        WebElement textCredentialPassword = row.findElement(By.id("credential-table-password"));
                        System.out.println("CREDENTIAL ENCRYPTED PASSWORD: " + credential.getPassword());
                        System.out.println("CREDENTIAL ENCRYPTED PASSWORD TABLE: " + textCredentialPassword.getAttribute("innerHTML"));
                        if (textCredentialUrl.getAttribute("innerHTML").equals(url) &&
                                textCredentialUsername.getAttribute("innerHTML").equals(username) &&
                                textCredentialPassword.getAttribute("innerHTML").equals(credential.getPassword())) {
                            cerdentialRow = row;
                            break;
                        }
                    }
                }
            }
        } catch (NoSuchElementException e) {
            // log as INFO?
        }
        return cerdentialRow;
    }
    //endregion



    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    @FindBy(id = "credentialEdit-password")
    private WebElement edit_cred_password_input;

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public WebElement getEdit_cred_password_input() {
        return edit_cred_password_input;
    }

    @FindBy(id = "nav-notes-tab")
    private WebElement notes_tab;

    @FindBy(id = "open-note-modal")
    private WebElement newNoteButton;

    @FindBy(id = "note-title")
    private WebElement note_title_input;

    @FindBy(id = "note-description")
    private WebElement note_desc_input;

    @FindBy(id = "save-note-button")
    private WebElement newNote_Submit;

    @FindBy(id = "note-edit-button")
    private WebElement note_edit_button;

    @FindBy(id = "editNote-title")
    private WebElement editNote_title;

    @FindBy(id = "editNote-description")
    private WebElement editNote_description;

    @FindBy(id = "edit-note-button")
    private WebElement edit_note_button;

    @FindBy(id = "note-delete-button")
    private WebElement note_delete_button;

    @FindBy(id = "userTable")
    private WebElement notesTable;

    @FindBy(id = "credentialTable")
    private WebElement credsTable;

    @FindBy(id = "nav-credentials-tab")
    private WebElement creds_tab;

    @FindBy(id = "open-credentials-modal")
    private WebElement newCredButton;

    @FindBy(id = "credential-url")
    private WebElement cred_url_input;

    @FindBy(id = "credential-username")
    private WebElement cred_username_input;

    @FindBy(id = "credential-password")
    private WebElement cred_password_input;

    @FindBy(id = "credential-submit")
    private WebElement cred_input_submit;

    @FindBy(id = "open-credentials-edit-modal")
    private WebElement editCredButton;

    @FindBy(id = "credentialEdit-url")
    private WebElement editCredUrl;

    @FindBy(id = "credentialEdit-username")
    private WebElement editCredUsername;

    @FindBy(id = "credentialEdit-password")
    private WebElement editCredPassword;

    @FindBy(id = "save-edit-credential")
    private WebElement editCredSave;

    @FindBy(id = "delete-credential")
    private WebElement deleteNoteBtn;
}
