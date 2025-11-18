package pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrderHistoryPage {

    private WebDriver driver;

    @FindBy(xpath = "//tbody/tr/th")
    private List<WebElement> orderIds;

    public OrderHistoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Validates if the given Order ID exists in the history table.
     */
    public boolean verifyOrderIdExists(String orderId) {
        return orderIds.stream().anyMatch(id -> id.getText().equalsIgnoreCase(orderId));
    }
}