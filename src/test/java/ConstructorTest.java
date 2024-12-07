import org.junit.*;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pageobject.MainPage;
import utilities.Base;

import utilities.WebDriverFactory;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)

public class ConstructorTest extends Base {

    private MainPage mainPage;

    public ConstructorTest(String browser) {

    }

    @Parameterized.Parameters(name = "Browser: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][]{
                        {"chrome"},
                        {"yandex"}
                }
        );
    }

    @Before
    public void setUp() {

        driver = WebDriverFactory.createDriver();
        driver.manage().window().maximize();
    }

    @Step("Открытие главной страницы")
    private void openMainPage() {
        driver.get(URL);
    }

    @Test
    @Description("Переход в раздел 'Булки'")
    public void checkBunSectionTest() {
        mainPage = new MainPage(driver);
        openMainPage();
        mainPage.searchSection("Булки");
        mainPage.isHeaderDisplayed("Булки");
    }

    @Test
    @Description("Переход в раздел 'Соусы'")
    public void checkSaucesSectionTest() {
        mainPage = new MainPage(driver);
        openMainPage();
        mainPage.searchSection("Соусы");
        mainPage.isHeaderDisplayed("Соусы");

    }

    @Test
    @Description("Переход в раздел 'Начинки'")
    public void checkFillingsSectionTest() {
        mainPage = new MainPage(driver);
        openMainPage();
        mainPage.searchSection("Начинки");
        mainPage.isHeaderDisplayed("Начинки");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
