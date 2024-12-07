package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utilities.ElementWaiter;
import java.time.Duration;

public class ProfilePage {

    private final WebDriver driver;
    private final ElementWaiter waiter;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.waiter = new ElementWaiter(driver, Duration.ofSeconds(20));
    }

    private final By pageLogo = By.xpath("//*[@id='root']/div/header/nav/div/a"); // Кнопка логотип
    private final By profileButton = By.xpath("//*[@id='root']/div/main/div/nav/ul/li[1]/a"); // Кнопка профиль
    private final By logoutButton = By.xpath("//button[contains(text(), 'Выход')]"); // Кнопка выход


//клик по кнопкам

    public void clickLogoButton() {
        driver.findElement(pageLogo).click();
    }

    public void clickLogoutButton() {
        driver.findElement(logoutButton).click();
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePageToLoad() {
        waiter.waitForElement(profileButton);
    }



}

