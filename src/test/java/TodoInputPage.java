import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TodoInputPage {
    protected WebDriver driver;
    private By inputBoxBy = By.id("todo-input");
    private By searchItemsInList = By.cssSelector("li:nth-child(1)");
    private By selectItemsInList = By.cssSelector("li:nth-child(1) .toggle");
    private By deleteItemsInList = By.cssSelector("li:nth-child(1) .destroy");
    private By completeAllItems = By.className("toggle-all");
    private By deleteCompleted = By.className("clear-completed");
    private By countRemaining = By.className("todo-count");


    // Navigating around the site
    public TodoInputPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateHome() {
        driver.get("https://todomvc.com/");
    }

    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist/");
    }

    // Inputting, editing, deleting items
    public void inputItem(String item) {
        WebElement inputItem = driver.findElement(inputBoxBy);
        inputItem.sendKeys(item+Keys.ENTER);
    }

    public void editItem(String newItem) {
        WebElement editItem = driver.findElement(searchItemsInList);
        Actions act = new Actions(driver);
        act.doubleClick(editItem).perform();
        editItem.sendKeys(Keys.CONTROL,"a", Keys.DELETE);
        editItem.sendKeys(newItem+Keys.ENTER);
    }

    public void deleteItem() throws InterruptedException {
        WebElement waitForDeletion = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> driver.findElement(deleteItemsInList));
        WebElement deleteItem = driver.findElement(deleteItemsInList);
        Thread.sleep(5000);
        deleteItem.click();
    }

    public void deleteList() {
        WebElement selectAll = driver.findElement(completeAllItems);
        selectAll.click();
        WebElement deleteAll = driver.findElement(deleteCompleted);
        deleteAll.click();
    }

    // Completing single item in list
    public void completeItem() {
        WebElement completeItem = driver.findElement(selectItemsInList);
        completeItem.click();

    }

    // Completing all items in list
    public void completeAll() {
        WebElement completeAll = driver.findElement(completeAllItems);
        completeAll.click();
    }


    // Returning items from list
    public String getFirstItem() {
        return driver.findElement(searchItemsInList).getText();

    }

    public String countRemaining() {
        return driver.findElement(countRemaining).getText();

    }

}