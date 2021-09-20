package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.CustomTest.Order;
import com.udacity.jwdnd.course1.cloudstorage.CustomTest.OrderedRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(OrderedRunner.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private WebDriverWait wait;

	SignUpPage signUpPage;
	LoginPage loginPage;
	HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(order = 1)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(order = 2)
	public void unauthorized()
	{
		//Assert: Not logged in
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("SignUp", driver.getTitle());
	}

	@Test
	@Order(order = 3)
	public void notExistingUser()
	{
		String username = "notExistingUser";
		String password = "anyPass";

		loginPage = new LoginPage();
		loginPage.toLogin(driver, this.port, wait);

		Assertions.assertEquals("Login", driver.getTitle());

		loginPage.login(username, password);

		WebElement loginFailedAlert = driver.findElement(By.className("alert alert-danger"));
		Assertions.assertTrue(loginFailedAlert.isDisplayed());
	}

	@Test
	@Order(order = 4)
	public void SignUpLoginTest()
	{
		String firstname = "test";
		String lastname = "user";

		String username = "Test.User";
		String password = "testpass";

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage = new SignUpPage();
		signUpPage.signUp(firstname, lastname, username, password);

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage();
		loginPage.getLoginUsername().sendKeys(username);
		loginPage.getLoginPassword().sendKeys(password);
		loginPage.getLoginButton().click();
		Assertions.assertEquals("Home", driver.getTitle());

	}

	@Test
	@Order(order = 5)
	public void logoutTest()
	{
		homePage = new HomePage();
		homePage.getLogoutButton().click();
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	@Order(order = 6)
	public void signUpAlreadyExistingUser()
	{
		String firstname = "test";
		String lastname = "user";

		String username = "Test.User";
		String password = "testpass";

		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage = new SignUpPage();
		signUpPage.signUp(firstname, lastname, username, password);

		WebElement signUpError = driver.findElement(By.className("alert alert-danger"));
		Assertions.assertTrue(signUpError.isDisplayed());
	}

	@Test
	@Order(order = 7)
	public void createNoteTest()
	{
		String username = "Test.User";
		String password = "testpass";

		String noteTitle = "newNoteTitleTest";
		String noteDesc = "newNoteDescTest";

		driver.get("http://localhost:" + this.port + "/login");
		loginPage = new LoginPage();
		loginPage.login(username, password);

		homePage = new HomePage();
		homePage.createNote(noteTitle, noteDesc);

		Assertions.assertTrue(driver.getCurrentUrl().contains("add-note-result"));

		Assertions.assertEquals("Result", driver.getTitle());
		Assertions.assertEquals(noteTitle, homePage.getNoteTitleDisplay().getText());
		Assertions.assertEquals(noteDesc, homePage.getNoteDescriptionDisplay().getText());
	}

	@Test
	@Order(order = 8)
	public void editNoteTest()
	{
		String newTitle = "UpdatedTitleTest";
		String newDesc = "UpdatedDescTest";

		createNoteTest();
		driver.get("http://localhost:" + this.port + "/home");

		homePage = new HomePage();
		homePage.editNote(newTitle, newDesc);

		Assertions.assertTrue(driver.getCurrentUrl().contains("update-note-result"));

		driver.get("http://localhost:" + this.port + "/home");
		homePage = new HomePage();
		homePage.getNotes_tab().click();

		Assertions.assertEquals(newTitle, homePage.getNoteTitleDisplay().getText());
		Assertions.assertEquals(newDesc, homePage.getNoteDescriptionDisplay().getText());
	}

	@Test
	@Order(order = 9)
	public void testDeleteNote(){
		String username = "Test.User";
		String password = "testpass";
		String noteTitle = "My note";
		String noteDescription = "This note is for testing";

		getLoginPage();
		loginPage = new LoginPage();
		loginPage.login(username, password);

		homePage = new HomePage();
		createNoteTest();


	//	//Deleting note and verifying that nothing is displayed
	//	homePage.deleteNote(noteTitle,noteDescription);
	//	Assertions.assertThrows(NoSuchElementException.class,()-> {
	//		homePage.getNoteTitle();
	//	});

	}

	public int getNotesSize(HomePage homePage)
	{
		int i = 0;
		for(WebElement temp : homePage.getnotetitledisplaylist())
			i++;
		return i;
	}
}
