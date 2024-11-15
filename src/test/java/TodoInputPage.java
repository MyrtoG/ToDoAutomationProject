import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TodoInputPage {
    protected WebDriver driver;
    private By inputBoxBy = By.id("todo-input");
    private By searchItemInList = By.cssSelector("li:nth-child(1)");
    private By deleteItemInList = By.cssSelector("li:nth-child(1) .destroy");
    private By completeAllItems = By.className("toggle-all");
    private By deleteCompleted = By.className("clear-completed");
    private By countRemaining = By.className("todo-count");
    private By clickItemByIndex(Integer index) {
        return By.cssSelector(String.format("li:nth-child(%s)",index));
    }
    private By toggleItemByIndex(Integer index) {
        return By.cssSelector(String.format("li:nth-child(%s) .toggle",index));
    }

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
        WebElement findItem = driver.findElement(searchItemInList);
        Actions action = new Actions(driver);
        action.doubleClick(findItem).perform();
        driver.findElement(By.cssSelector(".input-container:nth-child(1) > #todo-input")).sendKeys(Keys.COMMAND,"a",Keys.DELETE);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(findItem));
        driver.findElement(By.cssSelector(".input-container:nth-child(1) > #todo-input")).sendKeys(newItem,Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(findItem));
    }

    public void deleteItem() {
        WebElement deleteItem = driver.findElement(deleteItemInList);
        deleteItem.click();
    }

    public void deleteList() {
        WebElement selectAll = driver.findElement(completeAllItems);
        selectAll.click();
        WebElement deleteAll = driver.findElement(deleteCompleted);
        deleteAll.click();
    }

    // Completing single item in list by index
    public void completeItemByIndex(Integer index) {
        WebElement completeItemByIndex = driver.findElement(toggleItemByIndex(index));
        completeItemByIndex.click();
    }

    // Completing all items in list
    public void completeAll() {
        WebElement completeAll = driver.findElement(completeAllItems);
        completeAll.click();
    }

    // Returning items from list
    public String getIndexedItem(Integer index) {
        return driver.findElement(clickItemByIndex(index)).getText();
    }

    public String countRemaining() {
        return driver.findElement(countRemaining).getText();
    }

}
