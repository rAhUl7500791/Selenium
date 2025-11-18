package utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

    private WebDriver driver;
    private WebDriverWait wait;

    public SeleniumUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Wait for clickable (locator version)
    public void waitForElementClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }
    public void waitForElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    // Wait for clickable (WebElement version)
    public WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Screenshot utility
    public String getScreenshotPath(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }


}
