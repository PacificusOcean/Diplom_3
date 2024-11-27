import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ElementWaiter {

    private final WebDriver driver;
    private final Duration timeout; // Timeout value

    public ElementWaiter(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public void waitForElement(By locator) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Элемент не найден: " + locator, e);
        } catch (TimeoutException e) {
            throw new AssertionError("Элемент не найден в течение заданного времени: " + locator, e);
        }
    }
}