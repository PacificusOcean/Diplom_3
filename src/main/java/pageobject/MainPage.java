package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
      }
//элементы главной страницы

    private final By mainPageLogo = By.xpath("//a[@href='/']");//логотип центральный
    private final By constructorButton = By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p"); // Кнопка "Конструктор"
    private final By accountButton = By.xpath("//p[contains(text(),'Личный Кабинет')]"); //кнопка личный кабинет
    private final By orderButton = By.xpath(".//button[text()='Оформить заказ']"); //оформить заказ
    private final By loginButton = By.xpath("//*[@id='root']/div/main/section[2]/div/button"); // Кнопка войти в аккаунт видна только в яндексе
    private final By bunButton = By.xpath("//span[text()='Булки']/parent::div");// кнопка булки
    private final By saucesButton = By.xpath("//span[text()='Соусы']/parent::div");// кнопка соусы
    private final By fillingsButton = By.xpath("//span[text()='Начинки']/parent::div"); //кнопка начинки
    private final By bunsHeader = By.xpath("//h2[text()='Булки']");// раздел булки
    private final By saucesHeader = By.xpath("//h2[text()='Соусы']"); // раздел соусы
    private final By fillingsHeader = By.xpath("//h2[text()='Начинки']");//раздел начинки

    @Step("Открываем https://stellarburgers.nomoreparties.site/")
    public void openMainPage() {
        driver.get("https://stellarburgers.nomoreparties.site/");
    }
// кликаем по кнопкам
    public void clickMainPageLogo() {
        driver.findElement(mainPageLogo).click();
    }
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }
    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }
    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickBunButton() {
        driver.findElement(bunButton).click();
    }
    public void clickSaucesButton() {
        driver.findElement(saucesButton).click();
    }
    public void clickFillingsButton() {
        driver.findElement(fillingsButton).click();
    }

    public WebElement getElementByName(String elementName) {
        return driver.findElement(By.name(elementName));
    }



    public void getHeaderClass(String elementName) {
        switch (elementName.toLowerCase()) {
            case "булки":
                driver.findElement(bunsHeader).getAttribute("class");
                return;
            case "соусы":
                driver.findElement(saucesHeader).getAttribute("class");
                return;
            case "начинки":
                driver.findElement(fillingsHeader).getAttribute("class");
                return;
            default:
                throw new IllegalArgumentException("Такого раздела не существует: " + elementName);
        }
    }
    public By getAccountButton() {
        return accountButton;
    }
    public By getOrderButton() {
        return orderButton;
    }
    public By getLoginButton() {
        return loginButton;
    }

    @Step("Проверка видимости элемента: {locator}")
    public boolean isElementDisplayed(By locator, Duration timeout) {
        try {
            WebElement element = new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
