package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
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

	@Test
	public void SignUpLoginTest()
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
	}

	@Test
	public void logoutTest()
	{
		String username = "Test.User71";
		String password = "testpass";

		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);
		Assertions.assertEquals("Home", driver.getTitle());

		homePage = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getLogoutButton()));
		homePage.getLogoutButton().click();
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void signUpAlreadyExistingUser()
	{
		String firstname = "Testname";
		String lastname = "TestLastname";

		String username = "Test.User71";
		String password = "testpass";

		signUpPage = new SignUpPage(driver);
		signUpPage.toSignUp(port, driver, wait);
		signUpPage.signUp(firstname, lastname, username, password);

		WebElement signUpError = driver.findElement(By.id("signUpError"));
		Assertions.assertTrue(signUpError.isDisplayed());
	}

	@Test
	public void createNoteTest() throws InterruptedException {
		String username = "Test.User71";
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
		Assertions.assertEquals("Result", driver.getTitle());

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(driver);

		Assertions.assertTrue(homePage.checkForNote(noteTitle));
	}


	@Test
	public void createCredTest() throws InterruptedException {
		String username = "Test.User71";
		String password = "testpass";

		String credUrl = "newCredTitle";
		String credUser = "newCredUser";
		String credPW = "newCredPW";

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
		resultPage.returnToHome(driver);

		//Does not work for more than one credential for one website
		Assertions.assertTrue(homePage.checkForCred(credUrl, credUser));
	}

	@Test
	public void editExistingCred() throws InterruptedException {
		String username = "Test.User71";
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
		homePage.editCred(driver, credUrl, credUser, credPW);

		Assertions.assertTrue(driver.getCurrentUrl().contains("update-credential-result"));
		Assertions.assertEquals("Result", driver.getTitle());

		resultPage = new ResultPage(driver);
		resultPage.returnToHome(driver);

		//Does not work for more than one credential for one website
		Assertions.assertTrue(homePage.checkForCred(credUrl, credUser));
	}


	@Test
	public void editNoteTest() throws InterruptedException {
		String username = "Test.User71";
		String password = "testpass";

		String newTitle = "UpdatedTitleTest";
		String newDesc = "UpdatedDescTest";

		//Login
		loginPage = new LoginPage(driver);
		loginPage.toLogin(port, driver, wait);
		loginPage.login(username, password);

		homePage = new HomePage(driver);

		createNoteTest();

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);
		wait.until(ExpectedConditions.elementToBeClickable(homePage.getLogoutButton()));
		homePage.editNote(driver,newTitle, newDesc);

		Assertions.assertTrue(driver.getCurrentUrl().contains("update-note-result"));

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage(driver);

		Assertions.assertEquals(newTitle, homePage.getNoteTitleDisplay().getText());
		Assertions.assertEquals(newDesc, homePage.getNoteDescriptionDisplay().getText());
	}

	@Test
	public void testDeleteNote() throws InterruptedException {

		String username = "Test.User71";
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

		Assertions.assertTrue(driver.getCurrentUrl().contains("delete-credential-result"));
	}
}
