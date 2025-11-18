package pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

    private WebDriver driver;

    @FindBy(css = ".cartSection h3")
    private List<WebElement> cartProducts;
    
    @FindBy(xpath = "//button[contains(text(),'Checkout')]")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean verifyProductDisplay(String productName) {
        // Verify product in cart
        return cartProducts.stream().anyMatch(product -> product.getText().equals(productName));
    }
    
    /**
     * Clicks checkout and navigates to the Checkout Page.
     * Returns the NEXT page object (CheckoutPage).
     */
    public CheckoutPage goToCheckout() {
        checkoutButton.click();
        return new CheckoutPage(driver);
    }
}