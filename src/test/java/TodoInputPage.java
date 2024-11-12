import org.openqa.selenium.*;

public class TodoInputPage {
    protected WebDriver driver;
    private By inputBoxBy = By.id("todo-input");
    private By searchItemsInList = By.cssSelector("li:nth-child(1)");
    private By completeAllItems = By.className("toggle-all");
    private By deleteCompleted = By.className("clear-completed");

    public TodoInputPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigate() {
        driver.get("https://todomvc.com/examples/react/dist/");
    }

    public void inputItem(String item) {
        WebElement inputItem = driver.findElement(inputBoxBy);
        inputItem.sendKeys(item+Keys.ENTER);
    }

    public void deleteList() {
        WebElement selectAll = driver.findElement(completeAllItems);
        selectAll.click();
        WebElement deleteAll = driver.findElement(deleteCompleted);
        deleteAll.click();
    }

    public String getFirstItem() {
        return driver.findElement(searchItemsInList).getText();

    }

}