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

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class EnterByButtonTest extends Base {

    private String name;
    private String email;
    private String password;
    private final String browser;
    private Faker faker;
    private String shortPassword;
    private Base createUser;

    public EnterByButtonTest(String browser) {

        this.browser = browser;
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

        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();
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
    @Step("Нажатие на кнопку «Войти в аккаунт» на  странице логина")
    private void clickLoginButtonOnLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginButton();
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
            WebElement emailField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"));
            WebElement loginButton = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button")); // Robust locator
            return emailField.isDisplayed() && loginButton.isDisplayed();

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Один или несколько элементов на странице логина не найдены: " + e.getMessage());
            return false;
        }
    }

    @Step("Переход к странице восстановления пароля")
    private void goRecoveryPasswordPage() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordButton();
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
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickAccountButton();
    }

    @Step("Клик по кнопке конструктор из личного кабинета")
    private void clickConstructorButton() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickConstructor();
    }

    @Step("Проверка, что открыта страница личного кабинета")
    private void isOpenProfilePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/account/profile"));
        Assert.assertTrue("Не удалось перейти в личный кабинет.", driver.getCurrentUrl().contains("/account/profile"));
    }

    @Test
    @Description("Проверка входа по кнопке «Войти в аккаунт» на главной")
    public void loginButtonTest () {
        openMainPage();
        clickLoginButtonOnMainPage();
        isLoginPageLoaded();
        if (isLoginPageLoaded()) {
            System.out.println("Страница логина успешно загрузилась по кнопке «Войти в аккаунт»");
        } else {
            System.err.println("Страница логина не загрузилась по кнопке «Войти в аккаунт»");

        }

    }
    @Test
    @Description("Вход через кнопку «Личный кабинет»")
    public void loginAccountButtonTest() {
        openMainPage();
        clickAccountButtonOnMainPage();
        isLoginPageLoaded();
        if (isLoginPageLoaded()) {
            System.out.println("Страница логина успешно загрузилась через кнопку «Личный кабинет»");
        } else {
            System.err.println("Страница логина не загрузилась через кнопку «Личный кабинет»");

        }
    }

    @Step("Авторизация пользователя и переход в личный кабинет")
    private void loginAndGoToProfile() {
        driver.get(LOGIN_URL);

        try {
            WebElement emailField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"));
            emailField.sendKeys(createdUserEmail);

            WebElement passwordField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input"));
            passwordField.sendKeys("password123"); // Password used during registration

            clickLoginButtonOnLoginPage();

            System.out.println("Авторизация и переход в личный кабинет выполнены успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка авторизации или перехода на страницу профиля: " + e.getMessage());
            throw new AssertionError("Ошибка авторизации или перехода на страницу профиля", e);
        }
    }


    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void loginByLoginButtonRegisterPage() {
        openMainPage();
        openRegisterPage();
        clickLoginButtonRegisterPage();
        if (isLoginPageLoaded()) {
            System.out.println("Страница логина успешно загрузилась через кнопку «Войти»");
        } else {
            System.err.println("Страница логина не загрузилась через кнопку «Войти»");

        }
    }

    @Test
    @Description("Вход через кнопку в форме восстановления пароля.")
    public void LoginByLoginButtonRecoveryPasswordPageTest() {
        openMainPage();
        clickLoginButtonOnMainPage();
        clickLoginButtRecoveryPasswordPage();
        if (isLoginPageLoaded()) {
            System.out.println("Страница логина успешно загрузилась через кнопку «Войти» со страницы востановления пароля");
        } else {
            System.err.println("Страница логина не загрузилась через кнопку «Войти» со страницы востановления пароля");

        }
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
