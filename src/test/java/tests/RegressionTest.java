package tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pageobjects.CartPage;
import pageobjects.CheckoutPage;
import pageobjects.LoginPage;
import pageobjects.OrderConfirmationPage;
import pageobjects.OrderHistoryPage;
import pageobjects.ProductCataloguePage;
import resources.JsonDataReader;

public class RegressionTest extends BaseTest {

    @Test(groups = {"regression"}, dataProvider = "getE2EData", description = "Full E2E Purchase Flow and Order History Validation")
    public void e2ePurchaseAndHistoryValidation(HashMap<String, String> input) throws IOException {
    	
        ProductCataloguePage productCatalogue = loginPage.loginApplication(
                input.get("username"), 
                input.get("password")
        );
        
        // 2. Add Product to Cart
        productCatalogue.addProductToCart(input.get("productName"));

        // 3. Go to Cart Page and Verify Item
        CartPage cartPage = productCatalogue.goToCartPage();
        Assert.assertTrue(cartPage.verifyProductDisplay(input.get("productName")), 
                "Product was not correctly added to the cart: " + input.get("productName"));

        // 4. Go to Checkout Page
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        
        // 5. Place Order (Select Country and Submit)
        OrderConfirmationPage confirmationPage = checkoutPage
                .selectCountry(input.get("shippingCountry"))
                .submitOrder();
        
        // 6. Assert Order Confirmation and Capture ID
        String confirmationMsg = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmationMsg.equalsIgnoreCase("THANKYOU FOR THE ORDER."), 
                "Order confirmation message is incorrect.");
        
        // Note: In a real test, you'd clean the Order ID before using it in the next step
        String orderId = confirmationPage.getOrderId();
        
        // 7. Validate Order History
        OrderHistoryPage historyPage = confirmationPage.goToOrderHistory();
        Assert.assertTrue(historyPage.verifyOrderIdExists(orderId), 
                "New Order ID (" + orderId + ") was not found in the order history table for user " + input.get("username"));
    }
    
    /**
     * TestNG DataProvider to supply data from the JSON file.
     * The file path is built dynamically to support portability.
     */
    @DataProvider
    public Object[][] getE2EData() throws IOException {
        String filePath = System.getProperty("user.dir") + "/testdata.json";
        
        // ** UPDATED: Calling the static method directly **
        List<HashMap<String, String>> data = JsonDataReader.getJsonData(filePath);
        
        // Converts List<HashMap> to Object[][] required by TestNG
        // Each row [i] contains one element [0], which is a HashMap of all test data for that iteration.
        Object[][] testData = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            testData[i][0] = data.get(i);
        }
        return testData;
    }
}