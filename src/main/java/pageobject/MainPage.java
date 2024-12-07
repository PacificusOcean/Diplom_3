package pageobject;

import io.qameta.allure.Step;
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
    private final By saucesHeader = By.xpath("//section[1]/div[1]/div[2]/span"); // Кнопка "Соусы"
    private final By bunsHeader = By.xpath("//section[1]/div[1]/div[1]/span"); // Кнопка булки
    private final By fillingsHeader = By.xpath("//section[1]/div[1]/div[3]/span");//Кнопка начинки

   private final By bunsSection = By.xpath("//section[1]/div[1]/div[1]"); // Раздел "Булки"
   private final By saucesSection = By.xpath("//section[1]/div[1]/div[2]"); // Раздел "Соусы"
   private final By fillingsSection = By.xpath("//section[1]/div[1]/div[3]"); // Раздел "Начинки"

    // кликаем по кнопкам

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Проверка секции")
    public void searchSection(String sectionName) {
        By sectionLocator = getHeaderElement(sectionName);
        if ( isElementDisplayed (sectionLocator)) {
            switchAnotherHeader(sectionName);
        }
        clickHeader(sectionName);
    }

    @Step("Проверка, что раздел активен")
    public void isHeaderDisplayed(String sectionName) {
        By sectionLocator = getHeaderElement(sectionName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.attributeContains(sectionLocator, "class", "tab_tab_type_current__2BEPc"));
        assert isElementDisplayed(sectionLocator) : "Раздел '" + sectionName + "' не активен";

    }
  //Проверка видимости элемента

    private boolean isElementDisplayed(By sectionLocator) {
        return driver.findElement(sectionLocator).getAttribute("class").contains("tab_tab_type_current__2BEPc");
    }

    @Step("Нажатие на вкладку «Начинки»")
    public void clickFillingsHeader() {
        driver.findElement(fillingsHeader).click();
    }

    @Step("Нажатие на вкладку «Начинки»")
    public void clickSaucesHeader() {
        driver.findElement(saucesHeader).click();
    }

    @Step("Нажатие на вкладку «Начинки»")
    public void clickBunsHeader() {
        driver.findElement(bunsHeader).click();
    }
// Переключения по разделам

    private void clickHeader(String sectionName) {
        switch (sectionName.toLowerCase()) {
            case "начинки":
                clickFillingsHeader();
                break;
            case "соусы":
                clickSaucesHeader();
                break;
            case "булки":
                clickBunsHeader();
                break;
            default:
                throw new IllegalArgumentException("Неправильный раздел: " + sectionName);
        }
    }

    private void switchAnotherHeader(String currentSectionName) {
        switch (currentSectionName.toLowerCase()) {
            case "начинки":
                clickSaucesHeader();
                break;
            case "соусы":
                clickBunsHeader();
                break;
            case "булки":
                clickFillingsHeader();
                break;
            default:
                throw new IllegalArgumentException("Неправильный раздел: " + currentSectionName);
        }
    }

    public By getHeaderElement(String sectionName) {

        switch (sectionName.toLowerCase()) {
            case "булки":
                return bunsSection;
            case "соусы":
                return saucesSection;
            case "начинки":
                return  fillingsSection;
            default:
                throw new IllegalArgumentException("Такого раздела не существует: " + sectionName);
        }

        }
    }





