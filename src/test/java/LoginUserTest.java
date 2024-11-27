
import pageobject.*;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Step;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.ElementWaiter;
import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import utilities.WebDriverFactory;
import pageobject.*;
import utilities.Base;
import io.qameta.allure.junit4.DisplayName;
import utilities.*;



public class LoginTest extends Base {

    public LoginTest(String browser) {
        super(browser);
    }

    @Test
    @DisplayName("Успешный вход в личный кабинет")
    public void successLoginToAccountButton() {


    }
}
