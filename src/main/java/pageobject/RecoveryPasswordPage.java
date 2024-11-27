package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RecoveryPasswordPage {

    private final WebDriver driver;
    private final Duration timeout = Duration.ofSeconds(20);

    public RecoveryPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By constructorButton = By.xpath("//*[@id='root']/div/header/nav/ul/li[1]/a/p"); // Кнопка конструктор
    private final By pageLogo = By.xpath("//*[@id='root']/div/header/nav/div/a/svg"); // Кнопка логотип
    private final By accountButton = By.xpath("//*[@id='root']/div/header/nav/a/p"); // Кнопка личный кабинет
    private final By emailField = By.xpath("//*[@id='root']/div/main/div/form/fieldset/div/div/input"); // Поле для email
    private final By recoverButton = By.xpath("//*[@id='root']/div/main/div/div/p[2]/a"); // Кнопка восстановить
    private final By loginButton = By.xpath("//*[@id='root']/div/main/div/div/p/a"); //кнопка войти (Вспомнили пароль?)

    // кликаем по кнопкам

    public void clickPageLogo() {
        driver.findElement(pageLogo).click();
    }

    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    public void clickAccountButton() {
        driver.findElement(accountButton).click();
    }

    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    // Вводим email
    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Ожидание загрузки страницы восстановления пароля")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@class, 'recovery-password')]")));


    }
}