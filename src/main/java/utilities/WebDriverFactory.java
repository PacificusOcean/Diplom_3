package utilities;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException("Браузер -> " + browser + " не поддерживается");
        }
    }
 private static WebDriver createChromeDriver() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--start-maximized");
    options.addArguments("--remote-allow-origins=*");
    return new ChromeDriver(options);
    }
private static WebDriver createYandexDriver() {
        String yandexDriverPath = "C:\\\\cygwin64\\home\\Home\\Diplom\\yandexdriver-24.10.1.598-win64\\yandexdriver.exe"; // путь к драйверу у
        System.setProperty("webdriver.chrome.driver", yandexDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Home\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

}
