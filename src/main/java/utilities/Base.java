package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;

import org.json.JSONObject;

public abstract class Base {

protected WebDriver driver;
protected final String URL = "https://stellarburgers.nomoreparties.site/";

protected final String LOGIN_URL = "https://stellarburgers.nomoreparties.site/login";
protected String createdUserEmail = null;

    @Before

public void setup() {

        setupUser();
    }
    protected void setupUser() {
        String email = "test-user-" + System.currentTimeMillis() + "@example.com"; // Генерируем уникальный email
        String password = "password123";
        String name = "TestUser";


        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", name);

        try {
            Response response = RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody.toString())
                    .post("https://stellarburgers.nomoreparties.site/api/auth/register"); // Используем полный URL

            int statusCode = response.getStatusCode();

            if (statusCode == 200) {
                createdUserEmail = email;
                System.out.println("Пользователь " + email + " успешно создан.");
            } else {
                String errorMessage = "Ошибка при создании пользователя. Код состояния: " + statusCode + ". Ответ сервера: " + response.getBody().asString();
                System.err.println(errorMessage);
                throw new RuntimeException(errorMessage); // Перебрасываем исключение
            }
        } catch (Exception e) {
            String errorMessage = "Произошла ошибка при создании пользователя: " + e.getMessage();
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage, e); // Перебрасываем исключение с подробной информацией
        }
    }
protected void deleteUser() {
        try {
            if (createdUserEmail != null) {
                Response response = RestAssured.given()
                        .delete(URL + "api/auth/user/" + createdUserEmail);

                int statusCode = response.getStatusCode();

                if (statusCode == 204) {
                    System.out.println("Пользователь " + createdUserEmail + " успешно удален.");
                } else {
                    String errorMessage = "Ошибка удаления пользователя. Статус код: " + statusCode + ". Тело ответа: " + response.getBody().asString();
                    System.err.println(errorMessage);
                    throw new RuntimeException(errorMessage);
                }
            } else {
                System.err.println("Адрес электронной почты пользователя не найден. Удаление пропущено.");
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при удалении пользователя: " + e.getMessage());
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }
    }

    @After
public void tearDown() {
     if (driver != null) {
            driver.quit();
     }
     deleteUser(); // Вызываем метод удаления пользователя после каждого теста
  }
}



