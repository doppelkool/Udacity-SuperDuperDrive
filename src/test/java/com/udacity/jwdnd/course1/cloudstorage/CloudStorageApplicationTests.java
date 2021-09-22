package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;

	SignUpPage signUpPage;
	LoginPage loginPage;
	HomePage homePage;
	ResultPage resultPage;

	@Autowired
	private CredentialService credentialService;
	@Autowired
	private EncryptionService encryptionService;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 10);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * Write a test that verifies that an unauthorized user
	 * can only access the login and signup pages.
	 * */
	@Test
	public void unauthorized()
	{
		//Assert: Not logged in
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	/**
	 * Not an official Test
	 * Tries to login with invalid/non existing user credentials
	 * */
	@Test
	public void notExistingUser()
	{
		String username = "notExistingUser";
		String password = "anyPass";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(this.port, driver, wait);

		Assertions.assertEquals("Login", driver.getTitle());

		loginPage.login(username, password);

		Assertions.assertTrue(driver.getCurrentUrl().contains("error"));
	}

	/**
	 * Write a test that signs up a new user, logs in,
	 * verifies that the home page is accessible, logs out,
	 * and verifies that the home page is no longer accessible.
	 * */
	@Test
	public void SignUpLoginLogOutTest()
	{
		String firstname = "test";
		String lastname = "user";

		String username = "Test.User" + new Random().nextInt(1000);
		String password = "testpass";

		signUpPage = new SignUpPage(driver);
		signUpPage.toSignUp(port, driver, wait);
		signUpPage.signUp(firstname, lastname, username, password);

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		System.out.println("Username: " + username);
		System.out.println("Passwort: " + password);

		homePage = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getLogoutButton())).click();
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	/**
	 * Not an official Test
	 * Tests, an already existing user is capable of signing up again - is not the case
	 * */
	@Test
	public void signUpAlreadyExistingUser()
	{
		String firstname = "Testname";
		String lastname = "TestLastname";

		String username = "Test.User883";
		String password = "testpass";

		signUpPage = new SignUpPage(driver);
		signUpPage.toSignUp(port, driver, wait);
		signUpPage.signUp(firstname, lastname, username, password);

		WebElement signUpError = driver.findElement(By.id("signUpError"));
		Assertions.assertTrue(signUpError.isDisplayed());
	}

	/**
	 * Write a test that creates a note, and verifies it is displayed.
	 * */
	@Test
	public void createNoteTest() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		String noteTitle = "newTitle";
		String noteDesc = "newDescr";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		homePage.toHome(driver);
		homePage.navNotesTab(driver);
		homePage.navNewNote(driver);
		homePage.createNote(driver, noteTitle, noteDesc);

		Assertions.assertTrue(driver.getCurrentUrl().contains("add-note-result"));

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		Assertions.assertTrue(homePage.checkForNote(noteTitle));
	}

	/**
	 * Write a test that edits an existing note and verifies that the changes are displayed.
	 * */
	@Test
	public void editNoteTest() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		String newTitle = "UpdatedTitleTest";
		String newDesc = "UpdatedDescTest";

		//Login
		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		driver.get("http://localhost:" + this.port + "/home");
		homePage.toHome(driver);

		homePage.navNotesTab(driver);
		homePage.navEditNote(driver);
		homePage.editNote(driver,newTitle, newDesc);

		Assertions.assertTrue(driver.getCurrentUrl().contains("update-note-result"));

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		Assertions.assertTrue(homePage.checkForNote(newTitle));
	}

	/**
	 * Write a test that deletes a note and verifies that the note is no longer displayed.
	 * */
	@Test
	public void testDeleteNote() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		homePage.toHome(driver);

		//getNoteCount() -> 5
		//Delete (-1)
		//Verify: getNoteCount() -> 4
		int noteCount = homePage.getNoteCount();
		homePage.navNotesTab(driver);

		homePage.deleteNote(driver);
		Assertions.assertTrue(driver.getCurrentUrl().contains("delete-note-result"));

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		new HomePage(driver).toHome(driver);

		Assertions.assertEquals(homePage.getNoteCount(), noteCount - 1);
	}

	/**
	 * Write a test that creates a set of credentials,
	 * verifies that they are displayed,
	 * and verifies that the displayed password is encrypted.
	 * */
	@Test
	public void createCredTest() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		String credUrl = "newCredTitle" + new Random().nextInt(200);
		String credUser = "newCredUser" + new Random().nextInt(200);
		String credPW = "newCredPW" + new Random().nextInt(200);

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		homePage.toHome(driver);
		homePage.navCredTab(driver);
		homePage.navNewCred(driver);
		homePage.createCred(driver, credUrl, credUser, credPW);

		Assertions.assertTrue(driver.getCurrentUrl().contains("add-credential-result"));
		Assertions.assertEquals("Result", driver.getTitle());

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		homePage.toHome(driver);
		homePage.navCredTab(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getNewCredButton()));

		String key = credentialService.getCredentialByID(credentialService.getMaxIDFromCred()).getKey();
		Assertions.assertTrue(homePage.checkForCredentialEncryptedPassword(credUrl, credUser, encryptionService.encryptValue(credPW, key), credentialService));
	}

	/**
	 * Write a test that views an existing set of credentials,
	 * verifies that the viewable password is unencrypted,
	 * edits the credentials, and verifies that the changes are displayed.
	 * */
	@Test
	public void editExistingCred() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		String credUrl = "updatedCredUrl";
		String credUser = "updatedCredUser";
		String credPW = "updatedCredPW";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		homePage.toHome(driver);
		homePage.navCredTab(driver);
		homePage.navEditCred(driver);

		WebElement modalInputCredentialPassword = homePage.getEdit_cred_password_input();
		wait.until(ExpectedConditions.elementToBeClickable(modalInputCredentialPassword));
		String decryptedPassword = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value", modalInputCredentialPassword);

		String scriptHtml = "return document.getElementById('credentialEdit-id').getAttribute('value');";
		int credentialId = Integer.parseInt( ((JavascriptExecutor) driver).executeScript(scriptHtml).toString());
		Credential credential = credentialService.getCredentialByID(credentialId);

		Assertions.assertEquals(encryptionService.encryptValue(decryptedPassword, credential.getKey()),credential.getPassword());

		homePage.editCred(driver, credUrl, credUser, credPW);

		Assertions.assertTrue(driver.getCurrentUrl().contains("update-credential-result"));
		Assertions.assertEquals("Result", driver.getTitle());

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		//Does not work for more than one credential for one website
		Assertions.assertTrue(homePage.checkForCred(credUrl, credUser));
	}

	/**
	 * Write a test that deletes an existing set of credentials
	 * and verifies that the credentials are no longer displayed.
	 * */
	@Test
	public void deleteExistingCred() throws InterruptedException {
		String username = "Test.User883";
		String password = "testpass";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);
		homePage.toHome(driver);

		//getNoteCount() -> 5
		//Delete (-1)
		//Verify: getNoteCount() -> 4
		int credCount = homePage.getCredCount();
		homePage.navCredTab(driver);

		homePage.deleteCred(driver);
		Assertions.assertTrue(driver.getCurrentUrl().contains("delete-credential-result"));

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(wait);

		new HomePage(driver).toHome(driver);

		Assertions.assertEquals(homePage.getCredCount(), credCount - 1);
	}
}
