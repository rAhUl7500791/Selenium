package tests;


import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pageobjects.ProductCataloguePage;

public class SmokeTest extends BaseTest {

    // Reading default credentials from properties file (prop is inherited from BaseTest)
    // NOTE: This relies on prop being loaded in BaseTest.initializeDriver()
    

    @Test(groups = {"smoke"}, description = "Valid Login and Checkout Button Check")
    public void successfulLoginAndCheckoutButtonCheck() throws IOException {
        
        // 1. Successful Login (using BaseTest's inherited loginPage object)
        // We assume BaseTest's launchApplication sets the protected 'loginPage' field.
        ProductCataloguePage productCatalogue = loginPage.loginApplication("rahul@test1.com", "Test@123");
        
        // 2. Assert successful navigation
        Assert.assertNotNull(productCatalogue, "Login failed; Product Catalogue Page object is null.");
        
        // 3. Verify the "Checkout" button is present and displayed (Smoke Check)
        boolean isCheckoutPresent = productCatalogue.isCheckoutButtonPresent();
        Assert.assertTrue(isCheckoutPresent, "Checkout button is not present on the dashboard.");
    }
}