import org.junit.*;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import pageobject.MainPage;

import utilities.Base;

import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WebDriverFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;


@RunWith(Parameterized.class)

public class ConstructorTest extends Base{


    private Faker faker;

    public ConstructorTest(String browser) {

          faker = new Faker();
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

    @Step("Проверка и переход к разделу: {sectionName}")
    private void searchSection(String elementName) {
        MainPage mainPage = new MainPage(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {

            mainPage.getHeaderClass(elementName);

        } catch (Exception e) {
            //  обработка исключений
            System.err.println("Ошибка при переходе к разделу: " + elementName + "\n" + e.getMessage());
            throw new RuntimeException("Не удалось перейти к разделу " + elementName, e);
        }
    }


        @Test
    @Description("Переход в раздел 'Булки'")
    public void checkBunSectionTest() {
        openMainPage();
            try {
                searchSection("Булки");
                System.out.println("Переход в раздел 'Булки' выполнен успешно.");
            } catch (Exception e) {
                System.err.println("Ошибка перехода в раздел 'Булки': " + e.getMessage());
                throw new AssertionError("Ошибка перехода в раздел 'Булки'", e); // Re-throw to signal a failure
            }
    }

    @Test
    @Description("Переход в раздел 'Соусы'")
    public void checkSaucesSectionTest() {
        openMainPage();
        try {
            searchSection("Соусы");
            System.out.println("Переход в раздел 'Соусы' выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в раздел 'Соусы': " + e.getMessage());
            throw new AssertionError("Ошибка перехода в раздел 'Соусы'", e); // Re-throw to signal a failure
        }
    }

    @Test
    @Description("Переход в раздел 'Начинки'")
    public void checkFillingsSectionTest() {
        openMainPage();
        try {
            searchSection("Начинки");
            System.out.println("Переход в раздел 'Начинки' выполнен успешно.");
        } catch (Exception e) {
            System.err.println("Ошибка перехода в раздел 'Начинки': " + e.getMessage());
            throw new AssertionError("Ошибка перехода в раздел 'Начинки'", e); // Re-throw to signal a failure
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
