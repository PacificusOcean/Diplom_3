import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import utilities.Base;

import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;



import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import utilities.WebDriverFactory;

@RunWith(Parameterized.class)

public class LoginUserTest  extends Base {

    private String name;
    private String email;
    private String password;
    private final Faker faker = new Faker();
    private String shortPassword;

    public LoginUserTest(String browser) {

    }


    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        });
    }

    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver();
        driver.manage().window().maximize();


        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        shortPassword = "12345";
    }

    @Step("Открытие главной страницы")
    private void openMainPage() {
        driver.get(URL);
    }

    // Переход на страницу логина
    @Step("Открытие страницы логина")
    private void openLoginPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccountButton();
        String currentUrl = driver.getCurrentUrl();
        assertEquals("Страница открыта", LOGIN_URL, currentUrl);
    }

    @Step("Клик по кнопке зарегистрироваться на на странице логина")
    private void clickRegisterButtonOnLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterButton();
    }

    @Step("Клик по кнопке регистрации на странице регистрации")
    private void clickOnRegisterButton() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickRegisterButton();
    }

    @Step("Проверка перехода на страницу логина после регистрации")
    private void checkLoginPageAfterRegistration() {
        driver.get(LOGIN_URL);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement emailField = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input")));
            assertTrue(emailField.isDisplayed());

            WebElement passwordField = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input")));
            assertTrue(passwordField.isDisplayed());

            WebElement loginButton = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/button")));
            assertTrue(loginButton.isDisplayed());


        } catch (Exception e) {
            System.err.println("Ошибка при проверке элементов страницы логина: " + e.getMessage());
            fail();
        }

    }
    @Step("Проверка появления ошибки на странице регистрации")
    private void verifyErrorText(String expectedErrorMessage) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement errorTextElement = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[3]/div/p")));
            String actualErrorMessage = errorTextElement.getText();

            assertEquals(expectedErrorMessage, actualErrorMessage, "Некорректный пароль");
            System.out.println("Текст ошибки: " + actualErrorMessage);

        } catch (NoSuchElementException e) {
            fail("Элемент ошибки не найден."); // Убираем e
        } catch (TimeoutException e) {
            fail("Элемент ошибки не найден в течение заданного времени.");
        } catch (Exception e) {
            fail("Ошибка при проверке текста ошибки: " + e.getMessage());
        }
    }



    protected void isRegisterButton() {
        try {
            WebElement registerButton= driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button"));

            registerButton.isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Один или несколько элементов на странице логина не найдены: " + e.getMessage());
        }
    }


    @Test
    @Description("Тест успешной регистрации ")
    public void successfulRegistrationTest() {
        openMainPage();
        openLoginPage();
        clickRegisterButtonOnLoginPage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(password);
        clickOnRegisterButton();
        checkLoginPageAfterRegistration();
    }
    @Test
    @Description("Ошибка регистрации при попытке ввести короткий пароль")
    public void wrongRegistrationShortPassTest() {

        String expectedErrorMessage = "Неверный email или пароль.";
        openMainPage();
        openLoginPage();
        clickRegisterButtonOnLoginPage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(shortPassword);
        clickOnRegisterButton();
        verifyErrorText(expectedErrorMessage);

    }
    @Test
    @Description("Ошибка регистрации при попытке ввести пустой пароль")
    public void wrongRegistrationEmptyPassTest() {
        openMainPage();
        openLoginPage();
        clickRegisterButtonOnLoginPage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword("");
        isRegisterButton();
        clickOnRegisterButton();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

   }

