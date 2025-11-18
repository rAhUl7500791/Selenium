package pageobjects;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.SeleniumUtils;



/**
 * Page Object for the Product Catalogue/Dashboard screen.
 * Responsible for viewing products, adding items to the cart,
 * and navigating to the cart.
 */
public class ProductCataloguePage {

	private WebDriver driver;
    public SeleniumUtils utils; // Utility object for waits

    // Locators
    
    // Finds all product cards in the list
    @FindBy(css = ".mb-3")
    private List<WebElement> products;
    
    // Locator for the Cart button in the header
    @FindBy(css = "[routerlink*='cart']")
    private WebElement cartButton;

    // Placeholder locators for dynamic elements
    private By addToCartButton = By.cssSelector(".card-body button:last-of-type");
    private By productToastMessage = By.cssSelector("#toast-container"); 
    
    public ProductCataloguePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.utils = new SeleniumUtils(driver);
    }

    /**
     * Retrieves the entire list of product WebElements.
     */
    public List<WebElement> getProductList() {
        // Wait for all products to be visible on the page before interaction
        utils.waitForElementClickable(products.get(0)); 
        return products;
    }


    public WebElement getProductByName(String productName) {
        // Stream through the product list to find the matching product card
        WebElement prod = getProductList().stream()
                .filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        return prod;
    }
    

    public ProductCataloguePage addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        
        if (prod != null) {
            // Find the Add to Cart button within the specific product card
            prod.findElement(addToCartButton).click();
            
            // Explicit wait for the success toast message to appear
            utils.waitForElementClickable(productToastMessage); 
            
          
        } else {
            System.err.println("Product not found: " + productName);
        }
        return this;
    }

  
    public CartPage goToCartPage() {
        // Wait for the cart button to be clickable before clicking
        utils.waitForElementClickable(cartButton);
        cartButton.click();
        return new CartPage(driver);
    }
    
   
    public boolean isCheckoutButtonPresent() {
        // Check if the cart button locator is present on the page
        return cartButton.isDisplayed();
    }
}