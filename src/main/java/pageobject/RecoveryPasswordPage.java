package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecoveryPasswordPage {

    private final WebDriver driver;


    public RecoveryPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By recoverButton = By.xpath("//*[@id='root']/div/main/div/div/p[2]/a"); // Кнопка восстановить

    // кликаем по кнопке

    public void clickRecoverButton() {
        driver.findElement(recoverButton).click();
    }

   }