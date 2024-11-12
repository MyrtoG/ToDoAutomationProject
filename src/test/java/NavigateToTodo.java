import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class NavigateToTodo {
    public static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://todomvc.com/");
    }

    @Test
    void shouldLoadHomepage() throws Exception {
        assertEquals("TodoMVC", driver.getTitle());
        takeScreenshot(driver, "todo.png");
    }

    @Test
    void ShouldCheckValues() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // REMEMBER: Come back and add input using data driven testing
        // TEST: generic input should add to list
        inputPage.inputItem("hello123");
        assertEquals("hello123", inputPage.getFirstItem());
        inputPage.deleteList();

        // TEST: blank space as input should add to list
        inputPage.inputItem(" ");
        assertEquals(" ", driver.findElement(By.id("todo-input")).getAttribute("value"));
        inputPage.navigate();

        // TEST: no input as test should not add to list - will add later & speak to Neil
        //driver.findElement(By.id("todo-input")).click().sendKeys(Keys.ENTER);
        //assertEquals("What needs to be done?", driver.findElement(By.id("todo-input")).getAttribute("value"));

        // TEST: 255 characters and under as input should add to list
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.", inputPage.getFirstItem());
        inputPage.deleteList();
    }
        // TEST: over 255 characters as input should fail
    @Test
    void ShouldHaveCharLimit() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolores.");
        if (driver.findElement(By.cssSelector("li:nth-child(1)")).isDisplayed()) {
            fail("Your input is too long to add to the list");
        }
    }
        // TEST: symbols as input ("!@£$%^&*(){}[]:";'<>?")
        //inputPage.inputItem("!@£$%^&*(){}[]:";'<>?");
        //assertEquals("!@£$%^&*(){}[]:";'<>?", inputPage.getFirstItem());
        //inputPage.deleteList();
        // TEST:

    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }

    public static void takeScreenshot(WebDriver webDriver, String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot) webDriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
}

