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

    private final By accountButton = By.xpath("//*[@id='root']/div/header/nav/a/p"); // Кнопка личный кабинет

    private final By registerButton = By.xpath("//*[@id='root']/div/main/div/div/p[1]/a"); // Кнопка Зарегистрироваться

    // кликаем по кнопкам

    public void clickConstructor() {
        driver.findElement(constructorButton).click();
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    @Step("Проверка, что страница логина загружена")
    public boolean isLoginPageLoaded() {
        WebElement emailField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[1]/div/div/input"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/fieldset[2]/div/div/input"));
        WebElement loginButton = driver.findElement(By.xpath("//*[@id='root']/div/main/div/form/button"));

        return emailField.isDisplayed() && passwordField.isDisplayed() && loginButton.isDisplayed();

    }



}



