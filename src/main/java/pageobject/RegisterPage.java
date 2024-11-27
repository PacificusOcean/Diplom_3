package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class RegisterPage {

    private final WebDriver driver;
    private final Duration timeout = Duration.ofSeconds(10);

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By accountButton = By.xpath("//*[@id='root']/div/header/nav/a/p"); // Кнопка личный кабинет
    private final By nameField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input");
    private final By emailField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input");
    private final By passwordField = By.xpath("//*[@id='root']/div/main/div/form/fieldset[3]/div/div/input");
    private final By registerButton = By.xpath("//*[@id='root']/div/main/div/form/button"); //кнопка зарегистрироваться
    private final By errorText = By.xpath("//*[@id='root']/div/main/div/form/fieldset[3]/div/p"); // текс от ошибке пароля
    private final By loginButton = By.xpath("//*[@id='root']/div/main/div/div/p/a"); //кнопка войти

    // кликаем по кнопкам
    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // ввод данных
    public void enterName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Открытие и ожидание загрузки страницы регистрации")
    public void openAndWaitForRegisterPage() {
        driver.get("https://stellarburgers.nomoreparties.site/register");
        waitForElement(registerButton, timeout);
    }

    @Step("Ожидание видимости элемента: {locator}, таймаут: {timeout}")
    public void waitForElement(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Элемент не найден: " + locator, e);
        } catch (TimeoutException e) {
            throw new AssertionError("Элемент не найден в течение заданного времени: " + locator, e);
        }
    }
    public String getErrorText() {
        return driver.findElement(errorText).getText();
    }
}




