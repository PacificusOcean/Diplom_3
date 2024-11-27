package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ElementWaiter;
import java.time.Duration;
import java.time.Duration;


public class ProfilePage {

    private final WebDriver driver;
    private final ElementWaiter waiter;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.waiter = new ElementWaiter(driver, Duration.ofSeconds(20));
    }

    private final By constructorButton = By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p"); // Кнопка конструктор
    private final By pageLogo = By.xpath("//*[@id='root']/div/header/nav/div/a"); // Кнопка логотип
    private final By accountButton = By.xpath("//*[@id='root']/div/header/nav/a/p");// Кнопка личный кабинет
    private final By profileButton = By.xpath("//*[@id='root']/div/main/div/nav/ul/li[1]/a"); // Кнопка профиль
    private final By historyButton = By.xpath("//*[@id='root']/div/main/div/nav/ul/li[2]/a");// Кнопка история заказов
    private final By logoutButton = By.xpath("//button[contains(text(), 'Выход')]"); // Кнопка выход
    private final By cancelButton = By.xpath("//*[@id='root']/div/main/div/div/div/div/button[1]"); // Кнопка отмена
    private final By saveButton = By.xpath("//*[@id='root']/div/main/div/div/div/div/button[2]"); // Кнопка сохранить
    private final By nameField = By.xpath("//*[@id='root']/div/main/div/div/div/ul/li[1]/div/div/input"); // Поле имя
    private final By loginField = By.xpath("//*[@id='root']/div/main/div/div/div/ul/li[2]/div/div/input"); // Поле логин
    private final By passwordField = By.xpath("//*[@id='root']/div/main/div/div/div/ul/li[3]/div/div/input"); // Поле пароль

//клик по кнопкам

    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    public void clickLogoButton() {
        driver.findElement(pageLogo).click();
    }

    public void clickCancelButton() {
        driver.findElement(cancelButton).click();
    }

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void clickHistoryButton() {
        driver.findElement(historyButton).click();
    }

    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    public void clickSaveButton() {
        driver.findElement(saveButton).click();
    }

    public void clickProfileButton() {
        driver.findElement(profileButton).click();
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePageToLoad() {
        waiter.waitForElement(profileButton);
    }

    @Step("Получение содержимого из поля name")
    public String getNameFieldValue() {
        waitForProfilePageToLoad();
        return driver.findElement(nameField).getAttribute("value");
    }

    @Step("Получение содержимого из поля login")
    public String getLoginFieldValue() {
        waitForProfilePageToLoad();
        return driver.findElement(loginField).getAttribute("value");
    }

    @Step("Получение содержимого из поля login")
    public String getPasswordFieldValue() {
        waitForProfilePageToLoad();
        return driver.findElement(passwordField).getAttribute("value");
    }

// гетторы

    public By getNameField() {
        return nameField;
    }
    public By getLoginField() {
        return loginField;
    }
    public By getPasswordField() {
        return passwordField;
    }

    @Step("Проверка видимости логотипа")
    public boolean isPageLogoVisible() {
        try {
            waiter.waitForElement(pageLogo);
            return driver.findElement(pageLogo).isDisplayed();
        } catch (AssertionError e) {
            return false;
        }
    }
    @Step("Проверка видимости кнопки сохранть")
    public boolean isSaveButtonVisible() {
        try {
            waiter.waitForElement(saveButton);
            return driver.findElement(saveButton).isDisplayed();
        } catch (AssertionError e) {
            return false;
        }
    }
    @Step("Переход в конструктор через профиль")
    public void goToConstructorFromProfile() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickAccountButton();
        mainPage.isElementDisplayed(By.xpath("//p[contains(text(),'Личный Кабинет')]"), Duration.ofSeconds(10));
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Профиль']")));
        driver.findElement(By.xpath("//a[text()='Профиль']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p")));
        driver.findElement(By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p")).click();


    }

}

