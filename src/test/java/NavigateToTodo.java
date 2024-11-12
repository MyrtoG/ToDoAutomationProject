import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NavigateToTodo {
    public static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    void shouldLoadHomepage() throws Exception {
        driver.get("https://todomvc.com/");
        assertEquals("TodoMVC", driver.getTitle());
        takeScreenshot(driver, "todo.png");
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

