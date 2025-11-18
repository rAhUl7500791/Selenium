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

	/**
	 * Initializes the WebDriver based on system properties and loads global config.
	 * Configures browsers for headless execution if running in CI environment.
	 */
	public WebDriver initializeDriver() throws IOException {

		// 1. Load Properties File
		prop = new Properties();
		// NOTE: Path kept as per your input: src/main/java/resources/
		String configPath = System.getProperty("user.dir") + "/src/main/java/resources/GlobalConfig.properties";
		FileInputStream fis = new FileInputStream(configPath);
		prop.load(fis);

		// 2. Get browser and CI flag
		String browserProperty = System.getProperty("browser");
		String browserName;
		
		// FIX: Check if property is null OR empty/blank string, and default to "chrome"
		if (browserProperty == null || browserProperty.trim().isEmpty()) {
		    browserName = "chrome";
		} else {
		    browserName = browserProperty;
		}

		// Check for CI environment variable to enable headless mode
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
            
		} else {
            // CRITICAL: Throw an exception for unsupported/unrecognized browser names
            throw new IllegalArgumentException("Unsupported browser specified: '" + browserName + 
                "'. Please choose 'chrome', 'firefox', or 'edge'.");
        }

		// 4. Global Driver Settings
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		return driver;
	}

	/**
	 * Runs before every @Test: Initializes driver, navigates to the URL, and
	 * instantiates the starting page object.
	 */
	@BeforeMethod(alwaysRun = true)
	public void launchApplication() throws IOException {
		driver = initializeDriver();

		// Get environment URL (default to stage)
		String envName = System.getProperty("env") != null ? System.getProperty("env") : "stage";
		String url = prop.getProperty(envName + "Url");

		driver.get(url);

		// Instantiate the LoginPage object
		loginPage = new LoginPage(driver);
	}

	/**
	 * Runs after every @Test: Closes the browser instance.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
