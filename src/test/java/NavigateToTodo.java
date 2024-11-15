import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class NavigateToTodo {

    public static WebDriver driver1;
    public static WebDriver driver2;
    public static Map<WebDriver, String> Drivers;

    @BeforeAll
    static void launchBrowsers() {
        driver1 = new ChromeDriver();
        driver2 = new FirefoxDriver();
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        Drivers = Map.of(
                driver1, "Chrome",
                driver2, "Firefox"
                );
    }

    @Test
    void shouldLoadHomepage() throws Exception {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigateHome();
            assertEquals("TodoMVC", driver.getTitle());
            takeScreenshot(driver, String.format("todo_%s.png", Drivers.get(driver)));
        }
    }

    @DisplayName("Test input data")
    @ParameterizedTest(name = "Test input {0} should return {1}")
    @CsvFileSource(resources = "/input_data.csv")
    void shouldCheckValues(String inputData, String resultData) {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();
            inputPage.inputItem(inputData);
            assertEquals(resultData, inputPage.getIndexedItem(1));
            inputPage.deleteList();
        }
    }

    @Test
    void shouldTestNullValues() {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();
            inputPage.inputItem(" ");
            assertEquals(" ", driver.findElement(By.id("todo-input")).getAttribute("value"));
            inputPage.navigate();
        }
    }

    @Test
    void shouldEditDeleteValues() throws Exception {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();

            // Test Case 2: Editing an item
            inputPage.inputItem("hello123");
            assertEquals("hello123", inputPage.getIndexedItem(1));
            takeScreenshot(driver, String.format("1_%s.png", Drivers.get(driver)));
            inputPage.editItem("hello456");
            assertEquals("hello456", inputPage.getIndexedItem(1));
            takeScreenshot(driver, String.format("2_%s.png", Drivers.get(driver)));

            // Test Case 2: Deleting an item
            inputPage.inputItem("hello123");
            takeScreenshot(driver, String.format("preDelete_%s.png", Drivers.get(driver)));
            assertEquals("2 items left!", inputPage.countRemaining());
            inputPage.deleteItem();
            assertEquals("1 item left!", inputPage.countRemaining());
            takeScreenshot(driver, String.format("postDelete_%s.png", Drivers.get(driver)));
        }
    }

    @Test
    void shouldCompleteIncompleteValues() throws Exception {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();

            // Test Case 3: Marking items as complete and incomplete
            inputPage.inputItem("hello1");
            inputPage.inputItem("hello2");
            inputPage.inputItem("hello3");
            assertEquals("3 items left!", inputPage.countRemaining());
            // single complete
            inputPage.completeItemByIndex(1);
            assertEquals("2 items left!", inputPage.countRemaining());
            // all complete
            inputPage.completeAll();
            assertEquals("0 items left!", inputPage.countRemaining());
            // all incomplete
            inputPage.completeAll();
            assertEquals("3 items left!", inputPage.countRemaining());
            // all complete
            inputPage.completeAll();
            assertEquals("0 items left!", inputPage.countRemaining());
            // single incomplete
            inputPage.completeItemByIndex(2);
            assertEquals("1 item left!", inputPage.countRemaining());
            inputPage.deleteList();
        }
    }

    @Test
    void ShouldFilterClearCompletedValues() throws Exception {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();

            // Test Case 4: Filtering, reordering and clearing the list of items - Filtering
            inputPage.inputItem("hello1");
            inputPage.inputItem("hello2");
            inputPage.inputItem("hello3");
            inputPage.inputItem("hello4");
            assertEquals("4 items left!", inputPage.countRemaining());
            assertEquals("hello1", inputPage.getIndexedItem(1));

            //Complete two items
            inputPage.completeItemByIndex(1);
            inputPage.completeItemByIndex(2);
            assertEquals("2 items left!", inputPage.countRemaining());

            // Filter to Active view
            inputPage.driver.findElement(By.linkText("Active")).click();
            assertEquals("hello3", inputPage.getIndexedItem(1));
            assertEquals("hello4", inputPage.getIndexedItem(2));

            // Filter to Completed view
            driver.findElement(By.linkText("Completed")).click();
            assertEquals("hello1", inputPage.getIndexedItem(1));
            assertEquals("hello2", inputPage.getIndexedItem(2));

            // Test Case 4: Filtering, reordering and clearing the list of items - Clear completed
            // Filter back to All view
            driver.findElement(By.linkText("All")).click();
            assertEquals("hello1", inputPage.getIndexedItem(1));
            assertEquals("hello2", inputPage.getIndexedItem(2));
            assertEquals("hello3", inputPage.getIndexedItem(3));
            assertEquals("hello4", inputPage.getIndexedItem(4));
            driver.findElement(By.className("clear-completed")).click();
            assertEquals("hello3", inputPage.getIndexedItem(1));
            assertEquals("hello4", inputPage.getIndexedItem(2));
            inputPage.deleteList();
        }
    }

        // REMEMBER: These tests fail and need to be logged as bugs for the team to fix
    @Test
    void ShouldFailNeedsFix() throws Exception {
        for (WebDriver driver : Drivers.keySet()) {
            TodoInputPage inputPage = new TodoInputPage(driver);
            inputPage.navigate();

            // Test Case 1: Adding different values - over 255 characters as input should fail, currently it passes
            inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolores.");
            if (driver.findElement(By.cssSelector("li:nth-child(1)")).isDisplayed()) {
                fail("Your input is too long to add to the list");
            }
            inputPage.deleteList();

            // Test Case 1: Adding different values - over 1000 characters as input should fail, currently it passes
            inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.");
            if (driver.findElement(By.cssSelector("li:nth-child(1)")).isDisplayed()) {
                fail("Your input is too long to add to the list");
            }
            inputPage.deleteList();

            // Test Case 1: Adding different values - symbols as input ("!@£$%^&*(){}[]:";'<>?") should add to list, currently it doesn't
            inputPage.inputItem("!@£$%^&*(){}[]:\";'<>?");
            assertEquals("!@£$%^&*(){}[]:\";'<>?", inputPage.getIndexedItem(1));
            inputPage.deleteList();

            // Test Case 1: Adding different values - no input as test should not add to list - will add later & speak to Neil
            //driver.findElement(By.id("todo-input")).click().sendKeys(Keys.ENTER);
            //assertEquals("What needs to be done?", driver.findElement(By.id("todo-input")).getAttribute("value"));

        }
    }

    @AfterAll
    static void closeBrowser() {
        for (WebDriver driver : Drivers.keySet()) {
            driver.quit();
        }
    }

    public static void takeScreenshot(WebDriver webDriver, String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot) webDriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
}

