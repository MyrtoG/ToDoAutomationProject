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
    }

    @Test
    void shouldLoadHomepage() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigateHome();
        assertEquals("TodoMVC", driver.getTitle());
        takeScreenshot(driver, "todo.png");
    }

    @Test
    void shouldCheckValues() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // REMEMBER: Come back and add input using data driven testing
        // Test Case 1: Adding different values - generic input should add to list
        inputPage.inputItem("hello123");
        assertEquals("hello123", inputPage.getFirstItem());
        inputPage.deleteList();

        // Test Case 1: Adding different values - blank space as input should add to list - confirm with Myrto, technically should fail as we don't want blank spaces.
        inputPage.inputItem(" ");
        assertEquals(" ", driver.findElement(By.id("todo-input")).getAttribute("value"));
        inputPage.navigate();

        // Test Case 1: Adding different values - no input as test should not add to list - will add later & speak to Neil
        //driver.findElement(By.id("todo-input")).click().sendKeys(Keys.ENTER);
        //assertEquals("What needs to be done?", driver.findElement(By.id("todo-input")).getAttribute("value"));

        // Test Case 1: Adding different values - 255 characters as input should add to list
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.", inputPage.getFirstItem());
        inputPage.deleteList();

        // Test Case 1: Adding different values - 254 characters as input should add to list
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor.");
        assertEquals("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor.", inputPage.getFirstItem());
        inputPage.deleteList();

    }

    @Test
    void shouldEditDeleteValues() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // Test Case 2: Editing an item
        //inputPage.inputItem("hello123");
        //assertEquals("hello123", inputPage.getFirstItem());
        //takeScreenshot(driver, "preEdit.png");
        //inputPage.editItem("hello456");
        //takeScreenshot(driver, "postEdit.png");
        //assertEquals("hello456", inputPage.getFirstItem());

        // Test Case 2: Deleting an item
        //inputPage.inputItem("hello123");
        //assertEquals("hello123", inputPage.getFirstItem());
        //takeScreenshot(driver, "preDelete.png");
        //inputPage.deleteItem();
        //takeScreenshot(driver, "postDelete.png");
        //assertEquals("hello456", inputPage.getFirstItem());
    }

    @Test
    void shouldCompleteIncompleteValues() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // Test Case 3: Marking items as complete and incomplete
        inputPage.inputItem("hello1");
        inputPage.inputItem("hello2");
        inputPage.inputItem("hello3");
        assertEquals("3 items left!", inputPage.countRemaining());
        // single complete
        inputPage.completeItem1();
        assertEquals("2 items left!", inputPage.countRemaining());
        // all complete
        inputPage.completeAll();
        assertEquals("0 items left!", inputPage.countRemaining());
        // all incomplete
        inputPage.completeAll();
        assertEquals("3 items left!", inputPage.countRemaining());
        // all complete - gets repetitive here, perhaps we rearrange?
        inputPage.completeAll();
        assertEquals("0 items left!", inputPage.countRemaining());
        // single incomplete
        inputPage.completeItem1();
        assertEquals("1 item left!", inputPage.countRemaining());
        inputPage.deleteList();
    }

    @Test
    void ShouldFilterClearCompletedValues() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // Test Case 4: Filtering, reordering and clearing the list of items - Filter by completed
        inputPage.inputItem("hello1");
        inputPage.inputItem("hello2");
        inputPage.inputItem("hello3");
        inputPage.inputItem("hello4");
        assertEquals("4 items left!", inputPage.countRemaining());
        // Complete two items
        inputPage.completeItem1();
        inputPage.completeItem2();
        assertEquals("2 items left!", inputPage.countRemaining());
        // Filter to Active view
        // Add assertion
        // Filter to Completed view
        // Add assertion

        // Test Case 4: Filtering, reordering and clearing the list of items - Clear completed
        // Filter to All view
        // Add assertion
        // Clear completed
        // Add assertion
        inputPage.deleteList();

        // Test Case 4: Filtering, reordering and clearing the list of items - Reorder list (expected to fail)
        // Add inputs
        // Add steps to drag input
        // Add assertion
    }


        // REMEMBER: These tests fail and need to be logged as bugs for the team to fix
    @Test
    void ShouldFailNeedsFix() throws Exception {
        TodoInputPage inputPage = new TodoInputPage(driver);
        inputPage.navigate();

        // Test Case 1: Adding different values - over 255 characters as input should fail, currently it passes
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolores.");
        if (driver.findElement(By.cssSelector("li:nth-child(1)")).isDisplayed()) {
            fail("Your input is too long to add to the list");
        } inputPage.deleteList();

        // Test Case 1: Adding different values - over 1000 characters as input should fail, currently it passes
        inputPage.inputItem("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolore.");
        if (driver.findElement(By.cssSelector("li:nth-child(1)")).isDisplayed()) {
            fail("Your input is too long to add to the list");
        } inputPage.deleteList();

        // Test Case 1: Adding different values - symbols as input ("!@£$%^&*(){}[]:";'<>?") should add to list, currently it doesn't
        inputPage.inputItem("!@£$%^&*(){}[]:\";'<>?");
        assertEquals("!@£$%^&*(){}[]:\";'<>?", inputPage.getFirstItem());
        inputPage.deleteList();

    }

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

