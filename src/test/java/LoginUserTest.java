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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import utilities.WebDriverFactory;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class LoginUserTest  extends Base {

    private String name;
    private String email;
    private String password;
    private final String browser;
    private Faker faker;
    private String shortPassword;

    public LoginUserTest(String browser) {

        this.browser = browser;
        faker = new Faker();
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
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input")));

        //  Проверка элементов на странице логина
        if (!isLoginPageLoaded()) {
            throw new AssertionError("Страница логина не загрузилась корректно.");
        }
    }

    @Step("Проверка  появления ошибки на странице регистрации")
    private void verifyErrorText() {
        WebElement errorTextElement = null;
        try {
            errorTextElement = new WebDriverWait(driver, Duration.ofSeconds(30))
                    .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[3]/div/p")));


            String errorMessage = errorTextElement.getText();
            System.out.println("Текст ошибки: " + errorMessage);  // Output error message

        } catch (NoSuchElementException e) {
            System.err.println("Элемент ошибки не найден");
            throw new AssertionError("Элемент ошибки не найден", e);
        } catch (TimeoutException e) {
            System.err.println("Элемент ошибки не найден в течение заданного времени");
            throw new AssertionError("Элемент ошибки не найден в течение заданного времени", e);
        }
    }



    protected boolean isLoginPageLoaded() {
        try {
            WebElement emailField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"));
            WebElement loginButton = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button")); // Robust locator
            return emailField.isDisplayed() && loginButton.isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Один или несколько элементов на странице логина не найдены: " + e.getMessage());
            return false;
        }
    }

    protected boolean isRegisterButton() {
        try {
            WebElement registerButton= driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button"));

            return  registerButton.isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Один или несколько элементов на странице логина не найдены: " + e.getMessage());
            return false;
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
        openMainPage();
        openLoginPage();
        clickRegisterButtonOnLoginPage();
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.enterName(name);
        registerPage.enterEmail(email);
        registerPage.enterPassword(shortPassword);
        clickOnRegisterButton();
        verifyErrorText();

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

