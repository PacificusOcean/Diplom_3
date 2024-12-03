package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.ElementWaiter;

import java.time.Duration;


public class LoginPage {

    private final WebDriver driver;
    private final ElementWaiter waiter;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waiter = new ElementWaiter(driver, Duration.ofSeconds(20));
    }
    private final By constructorButton = By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p"); // Кнопка конструктор
    private final By pageLogo = By.xpath("//*[@id='root']/div/header/nav/div/a/svg"); // Кнопка логотипа
    private final By accountButton = By.xpath("//*[@id='root']/div/header/nav/a/p"); // Кнопка личный кабинет
    private final By emailField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"); // Поле email
    private final By passwordField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input"); // Поле password
    private final By loginButton = By.xpath("//*[@id='root']/div/main/div/form/button"); // Кнопка войти
    private final By registerButton = By.xpath("//*[@id='root']/div/main/div/div/p[1]/a"); // Кнопка Зарегистрироваться
    private final By recoverPassword = By.xpath("//*[@id='root']/div/main/div/div/p[2]/a"); // Кнопка восстановить пароль

    // кликаем по кнопкам

    public void clickLogoButton() {
        driver.findElement(pageLogo).click();
    }
    public void clickConstructor() {
        driver.findElement(constructorButton).click();
    }
    public void clickLoginButton() {
        driver.findElement(accountButton).click();
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickRecoverPasswordButton() {
        driver.findElement(recoverPassword).click();
    }
    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }
    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Проверка, что страница логина загружена")

    protected boolean isLoginPageLoaded() {
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        return emailField.isDisplayed() && passwordField.isDisplayed() && loginButton.isDisplayed();

    }

    // Локаторы

    public By getPageLogo() {
        return pageLogo;
    }

    public By getConstructorButton() {
        return constructorButton;
    }

    public By getAccountButton() {
        return accountButton;
    }

    public By getEmailFieldLocator() {
        return emailField;
    }

    public By getPasswordFieldLocator() {
        return passwordField;
    }

    public By getLoginButtonLocator() {
        return loginButton;
    }

    public By getRegisterButtonLocator() {
        return registerButton;
    }

    public By getRecoverPasswordButton() {
        return recoverPassword;
    }

}



