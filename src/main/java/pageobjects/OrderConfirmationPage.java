package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderConfirmationPage {

    private WebDriver driver;

    @FindBy(css = ".hero-primary")
    private WebElement confirmationMessage;
    
    @FindBy(css = ".orderDetails span")
    private WebElement orderIdElement;

    public OrderConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }
    
    public String getOrderId() {
        // This is simplified; the actual ID is typically formatted.
        // Needs trimming and cleaning logic.
        return orderIdElement.getText(); 
    }
    
    // Returns the same page object
    public OrderHistoryPage goToOrderHistory() {
        // Logic to click on the 'Orders' tab in the header
        // Using placeholder for simplicity
        driver.findElement(org.openqa.selenium.By.cssSelector("[routerlink='/dashboard/myorders']")).click();
        return new OrderHistoryPage(driver);
    }
}