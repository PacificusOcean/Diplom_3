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

    private final By recoverButton = By.xpath("//*[@id='root']/div/main/div/div/p[2]/a"); // Кнопка восстановить

    // кликаем по кнопкам

    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

    @Step("Ожидание загрузки страницы восстановления пароля")
    public void waitForPageToLoad() {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[contains(@class, 'recovery-password')]")));
    }
}