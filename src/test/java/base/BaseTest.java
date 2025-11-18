package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.LoginPage;

public class BaseTest {

    public WebDriver driver;
    public Properties prop;

    protected LoginPage loginPage;

    public WebDriver initializeDriver() throws IOException {

        // 1. Load Properties File
        prop = new Properties();
        String configPath = System.getProperty("user.dir") + "/src/main/java/resources/GlobalConfig.properties";
        FileInputStream fis = new FileInputStream(configPath);
        prop.load(fis);

        // 2. Get browser and CI flag
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";
        boolean isCI = System.getenv("CI") != null && System.getenv("CI").equals("true");

        // 3. Initialize driver based on browser
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isCI) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-gpu");
                options.addArguments("--disable-dev-shm-usage");
            }
            driver = new ChromeDriver(options);

        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isCI) {
                options.addArguments("--headless");
            }
            driver = new FirefoxDriver(options);

        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            EdgeOptions options = new EdgeOptions();
            if (isCI) {
                options.addArguments("--headless=new");
            }
            driver = new EdgeDriver(options);
        }

        // 4. Global Driver Settings
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void launchApplication() throws IOException {
        driver = initializeDriver();

        // Get environment URL (default to stage)
        String envName = System.getProperty("env") != null ? System.getProperty("env") : "stage";
        String url = prop.getProperty(envName + "Url");

        driver.get(url);

        // Save for test usage
        loginPage = new LoginPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
