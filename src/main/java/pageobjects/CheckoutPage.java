package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage {

    private WebDriver driver;

    @FindBy(xpath = "//input[@placeholder='Select Country']")
    private WebElement countryInput;
    
    @FindBy(xpath = "//section[contains(@class,'ta-results')]//button[2]") // Assuming first result
    private WebElement selectCountryButton;
    
    @FindBy(css = ".action__submit")
    private WebElement placeOrderButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Selects the shipping country.
     */
    public CheckoutPage selectCountry(String countryName) {
        countryInput.sendKeys(countryName.substring(0, 3)); // Type first 3 letters
        // Simple selection: click the suggested country (needs better wait logic)
        selectCountryButton.click(); 
        return this;
    }
    
    /**
     * Clicks place order and navigates to the Confirmation Page.
     * Returns the NEXT page object (OrderConfirmationPage).
     */
    public OrderConfirmationPage submitOrder() {
        placeOrderButton.click();
        return new OrderConfirmationPage(driver);
    }
}