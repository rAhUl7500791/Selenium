package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import utils.SeleniumUtils;

/**
 * Custom TestNG Listener to integrate with ExtentReports.
 * Handles test start, success, and captures screenshots on failure.
 */
public class TestListener implements ITestListener {
    
    // ExtentReports object initialized from the utility class
    ExtentReports extent = ExtentReporterNG.getReporterObject();
    
    // ThreadLocal ensures thread-safety when running tests in parallel
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); 

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new entry in the report for the starting test method
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        extentTest.get().log(Status.INFO, "Starting Test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // 1. Log Failure and Throwable
        extentTest.get().fail(result.getThrowable());
        
        // 2. Take Screenshot and Attach to Report
        try {
            // Get the WebDriver instance from BaseTest (using Reflection)
            // It navigates to the parent class (BaseTest) and finds the 'driver' field.
            WebDriver driver = (WebDriver) result.getTestClass().getRealClass()
                    .getSuperclass().getDeclaredField("driver")
                    .get(result.getInstance());
            
            // Use the utility to capture and save the screenshot
            String screenshotPath = new SeleniumUtils(driver).getScreenshotPath(
                    result.getMethod().getMethodName(), driver);
            
            // Attach screenshot to the report
            extentTest.get().addScreenCaptureFromPath(screenshotPath, 
                    result.getMethod().getMethodName());
            
        } catch (Exception e) {
            e.printStackTrace();
            extentTest.get().log(Status.FAIL, "Could not capture screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void onFinish(ITestContext context) {
        // Flushes the report, writing all logs and results to the HTML file
        extent.flush();
    }
    
    // The other methods (onTestSkipped, onStart) are omitted for brevity...
}