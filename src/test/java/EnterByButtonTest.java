import org.junit.*;
import pageobject.*;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;

import org.openqa.selenium.WebElement;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import utilities.Base;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WebDriverFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


@RunWith(Parameterized.class)

public class EnterByButtonTest extends Base {
    LoginPage loginPage = new LoginPage(driver);
    private final Faker faker;


    public EnterByButtonTest(String browser) {

        faker = new Faker();
    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                {"chrome"},
                {"yandex"}
        }
        );
    }
    @Before
    public void setUp() {

        driver = WebDriverFactory.createDriver();
        driver.manage().window().maximize();


    }
    @Step("Открытие главной страницы")
    private void openMainPage() {
        driver.get(URL);
    }

    @Step("Нажатие на кнопку «Войти в аккаунт» на главной странице")
    private void clickLoginButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
    }


    @Step("Нажатие на кнопку «Личный кабинет» на главной странице")
    private void clickAccountButtonOnMainPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccountButton();
    }



    @Step("Нажатие на кнопку «Личный кабинет» на главной странице")
    private void clickLogoutButtonOnProfilePage() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.waitForProfilePageToLoad();
        profilePage.clickLogoutButton();
    }


    @Step("Открываем страницу регистрации")
    private void openRegisterPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterButton();
    }
    @Step("Нажатие на кнопку Войти на страницы регистрации")
    private void clickLoginButtonRegisterPage() {
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginButton();
    }
    protected boolean isLoginPageLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement emailField = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input")));
            WebElement loginButton = wait.until(presenceOfElementLocated(By.xpath("//*[@id='root']/div/main/div/form/button")));
            assertTrue(emailField.isDisplayed());
            assertTrue(loginButton.isDisplayed());
            return true;
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке страницы логина: " + e.getMessage());

            return false;
        }

    }
    protected boolean isProfilePageLoaded() {
        try {
            //Ожидаем, пока элемент "Профиль" будет отображаться
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement profileLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Профиль")));

            return profileLink.isDisplayed();
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке страницы профиля: " + e.getMessage());
            return false;
        }
    }


    @Step("Нажатие на кнопку Вход на странице восстановления пароля")
    private void clickLoginButtRecoveryPasswordPage() {
        RecoveryPasswordPage  recoveryPasswordPage = new  RecoveryPasswordPage(driver);
        recoveryPasswordPage.clickRecoverButton();
    }

    @Step("Клик по логотипу из личного кабинета")
    private void clickPageLogo () {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoButton();
    }

    @Step("Клик по кнопке личный кабинет из личного кабинета")
    private void clickAccountButtonOnLoginPage() {

        loginPage.clickAccountButton();
    }

    @Step("Клик по кнопке конструктор из личного кабинета")
    private void clickConstructorButton() {

        loginPage.clickConstructor();
    }

    @Test
    @Description("Проверка входа по кнопке «Войти в аккаунт» на главной")
    public void loginButtonTest () {
        openMainPage();
        clickLoginButtonOnMainPage();
        isLoginPageLoaded();
        assertTrue(isLoginPageLoaded());

        System.out.println("Страница логина успешно загрузилась по кнопке «Войти в аккаунт»");

        }


    @Test
    @Description("Вход через кнопку «Личный кабинет»")
    public void loginAccountButtonTest() {
        openMainPage();
        clickAccountButtonOnMainPage();
        isLoginPageLoaded();
        assertTrue(isLoginPageLoaded());
            System.out.println("Страница логина успешно загрузилась через кнопку «Личный кабинет»");

    }
    @Step("Авторизация пользователя и переход в личный кабинет")
    private void loginAndGoToProfile() {
        driver.get(LOGIN_URL); // Открываем страницу входа

        try {
            WebElement emailField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"));
            emailField.sendKeys(createdUserEmail);

            WebElement passwordField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input"));
            passwordField.sendKeys("password123");

            WebElement loginButton = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button"));
            loginButton.click();

            WebElement accountButton = driver.findElement(By.xpath("//*[@id='root']/div/header/nav/a/p"));
            accountButton.click();

            // Ожидаем, пока появится элемент "Мой профиль"
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/main/div/nav/ul/li[1]/a"))); // Используем elementToBeClickable

            WebElement profileLink = driver.findElement(By.xpath("//*[@id='root']/div/main/div/nav/ul/li[1]/a"));
            profileLink.click();

            //  Проверка, что мы на странице профиля
            assertTrue(isProfilePageLoaded());
            System.out.println("Авторизация и переход в личный кабинет выполнены успешно.");

        } catch (Exception e) {
            System.err.println("Ошибка авторизации или перехода на страницу профиля: " + e.getMessage());
            fail();
        }
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void loginByLoginButtonRegisterPage() {
        openMainPage();
        openRegisterPage();
        clickLoginButtonRegisterPage();
        isLoginPageLoaded();
        assertTrue(isLoginPageLoaded());

            System.out.println("Страница логина успешно загрузилась через кнопку «Войти» в форме регистрации");

        }

    @Test
    @Description("Вход через кнопку в форме восстановления пароля.")
    public void LoginByLoginButtonRecoveryPasswordPageTest() {
        openMainPage();
        clickLoginButtonOnMainPage();
        clickLoginButtRecoveryPasswordPage();
        isLoginPageLoaded();
        assertTrue(isLoginPageLoaded());

            System.out.println("Страница логина успешно загрузилась через кнопку «Войти» со страницы востановления пароля");

          }

    @Test
    @Description("Переход в личный кабинет")
    public void transferToPersonalAccountTest() {
        setupUser();
        if(createdUserEmail == null){
            throw new RuntimeException("Пользователь не зарегистрирован!");
        }
        openMainPage();
        clickAccountButtonOnMainPage();
        loginAndGoToProfile();
    }

    @Test
    @Description("Переход из личного кабинета в конструктор")
    public void transferFromPersonalAccountToConstructorTest() {
        setupUser();
        if(createdUserEmail == null){
            throw new RuntimeException("Пользователь не зарегистрирован!");
        }
        openMainPage();
        clickAccountButtonOnMainPage();
        loginAndGoToProfile();
        try {
            clickConstructorButton();
            System.out.println("Переход в конструктор выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в конструктор: " + e.getMessage());
            throw new AssertionError("Ошибка перехода в конструктор", e);
        }
    }

    @Test
    @Description("Проверь в конструктор по клику на логотип Stellar Burgers из личного кабинета")
    public void transferStellarBurgersToConstructorTest() {
        setupUser();
        if(createdUserEmail == null){
            throw new RuntimeException("Пользователь не зарегистрирован!");
        }
        openMainPage();
        clickAccountButtonOnMainPage();
        loginAndGoToProfile();

        try {
            clickPageLogo();
            System.out.println("Переход в конструктор выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в конструктор: " + e.getMessage());
            throw new AssertionError("Ошибка перехода в конструктор", e);
        }
    }

    @Test
    @Description("Выход по кнопке «Выйти» в личном кабинете")
    public void LogoutTest() {

        setupUser();
        if(createdUserEmail == null){
            throw new RuntimeException("Пользователь не зарегистрирован!");
        }
        openMainPage();
        clickAccountButtonOnMainPage();
        loginAndGoToProfile();
        clickAccountButtonOnLoginPage();
        try{
            clickLogoutButtonOnProfilePage();
            System.out.println("Выход из личного кабинета выполнен успешно.");

            (new WebDriverWait(driver, Duration.ofSeconds(10)))
                    .until(ExpectedConditions.urlMatches(".*stellarburgers.*"));

            System.out.println("Выход из личного кабинета подтвержден.");

        } catch (Exception e) {
            System.err.println("Ошибка при выходе из личного кабинета: " + e.getMessage());
            throw new AssertionError("Ошибка при выходе из личного кабинета", e);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
