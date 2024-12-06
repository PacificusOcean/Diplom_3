package pageobject;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public  class MainPage {

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
//элементы главной страницы

    private final By accountButton = By.xpath("//p[contains(text(),'Личный Кабинет')]"); //кнопка личный кабинет
    private final By loginButton = By.xpath("//*[@id='root']/div/main/section[2]/div/button"); // Кнопка войти в аккаунт видна только в яндексе
    private final By bunsHeader = By.xpath("//h2[text()='Булки']");// раздел булки
    private final By saucesHeader = By.xpath("//h2[text()='Соусы']"); // раздел соусы
    private final By fillingsHeader = By.xpath("//*[@id=\"root\"]/div/main/section[1]/div[1]/div[3]/span");//раздел начинки

    // кликаем по кнопкам

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Проверка видимости элемента")
    public boolean isElementDisplayed(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void searchSection(String elementName) {
        WebElement element = getHeaderElement(elementName);
        if (element != null) {
            element.click();

            Assert.assertTrue("Страница раздела не загрузилась.", isElementDisplayed(By.xpath("//h2[text()='" + elementName + "']")));

        } else {
            throw new RuntimeException("Элемент раздела '" + elementName + "' не найден.");
        }
    }
    @Step("Проверка отображения заголовка {sectionName}")
    public boolean isSectionDisplayed(String sectionName) {
        WebElement header = getHeaderElement(sectionName);
        return header != null && header.isDisplayed();
    }

    public WebElement getHeaderElement(String sectionName) {
        By locator;

        switch (sectionName.toLowerCase()) {
            case "булки":
                locator = bunsHeader;
                break;
            case "соусы":
                locator = saucesHeader;
                break;
            case "начинки":
                locator = fillingsHeader;
                break;
            default:
                throw new IllegalArgumentException("Такого раздела не существует: " + sectionName);
        }

        try {
            return new WebDriverWait(driver, Duration.ofSeconds(15))
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            return null;
        }
    }
}



