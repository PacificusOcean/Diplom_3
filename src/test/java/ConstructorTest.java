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
        openMainPage();
        MainPage mainPage = new MainPage(driver);

        try {
            boolean isBunsDisplayed = mainPage.isSectionDisplayed("Булки");
            Assert.assertTrue("Раздел 'Булки' не отображается.", isBunsDisplayed);

            } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
            throw new AssertionError("Ошибка перехода в раздел 'Булки'", e);
        }

    }

    @Test
    @Description("Переход в раздел 'Соусы'")
    public void checkSaucesSectionTest() {
        openMainPage();
        MainPage mainPage = new MainPage(driver);
        try {
            boolean isSaucesDisplayed = mainPage.isSectionDisplayed("Соусы");
            Assert.assertTrue("Раздел 'Соусы' не отображается.", isSaucesDisplayed);
            mainPage.searchSection("Соусы");
            System.out.println("Переход в раздел 'Соусы' выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в раздел 'Соусы': " + e.getMessage());
            e.printStackTrace();
            throw new AssertionError("Ошибка перехода в раздел 'Соусы'", e);
        }
    }

    @Test
    @Description("Переход в раздел 'Начинки'")
    public void checkFillingsSectionTest() {
        openMainPage();
        MainPage mainPage = new MainPage(driver);
        try {
            boolean isFillingsDisplayed = mainPage.isSectionDisplayed("Начинки");
            Assert.assertTrue("Раздел 'Начинки' не отображается.", isFillingsDisplayed);

            mainPage.searchSection("Начинки");
            System.out.println("Переход в раздел 'Начинки' выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в раздел 'Начинки': " + e.getMessage());
            e.printStackTrace();
            throw new AssertionError("Ошибка перехода в раздел 'Начинки'", e);
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
