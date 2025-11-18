package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.SeleniumUtils;

public class LoginPage {
	WebDriver driver;
	SeleniumUtils utils;
	@FindBy(id = "userEmail")
    private WebElement userEmailField;

    @FindBy(id = "userPassword")
    private WebElement userPasswordField;

    @FindBy(id = "login")
    private WebElement loginButton;
    
    @FindBy(css = "[class*='flyInOut']") // General locator for the error toast message
    private WebElement errorMessageToast;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.utils = new SeleniumUtils(driver);
    }

    /**
     * Business method combining the login steps into a single call.
     * Returns the ProductCataloguePage object upon successful login.
     */
    public ProductCataloguePage loginApplication(String email, String password) {
    	utils.waitForElementVisible(userEmailField);
        userEmailField.sendKeys(email);
        utils.waitForElementVisible(userPasswordField);
        userPasswordField.sendKeys(password);
        utils.scrollIntoView(loginButton);
        utils.waitForElementClickable(loginButton);
        loginButton.click();
        return new ProductCataloguePage(driver);
    }

    // Method Chaining: Returns 'this' (LoginPage)
//    public LoginPage enterEmail(String email) {
//        userEmailField.sendKeys(email);
//        return this; 
//    }
//
//    // Method Chaining: Returns 'this' (LoginPage)
//    public LoginPage enterPassword(String password) {
//        userPasswordField.sendKeys(password);
//        return this; 
//    }
//
//    // Method Chaining: Returns the NEXT page object (ProductCataloguePage)
//    public ProductCataloguePage clickLogin() {
//        loginButton.click();
//        // Assume successful login leads to Product Catalogue page
//        return new ProductCataloguePage(driver);
//    }
//    
//    // Business Method for Invalid Login
//    public LoginPage loginInvalid(String email, String password) {
//        enterEmail(email);
//        enterPassword(password);
//        loginButton.click();
//        // Stays on the same page
//        return this; 
//    }
//    
//    public String getErrorMessage() {
//        // In a real scenario, you'd use a wait utility here for the toast to appear
//        return errorMessageToast.getText();
//    }
}
