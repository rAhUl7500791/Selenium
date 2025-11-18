package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
    static ExtentReports extent;

    public static ExtentReports getReporterObject() {

//        String path = System.getProperty("user.dir") + "/reports/index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");

        // ---------- Report UI Settings ----------
        reporter.config().setTheme(Theme.DARK);                       // BEAUTIFUL DARK THEME
        reporter.config().setReportName("E2E Automation Report");     // Top header name
        reporter.config().setDocumentTitle("Test Execution Report");  // Browser tab title
        reporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");  // Better timestamps
        reporter.config().setEncoding("UTF-8");                       // For symbols, fonts
        //reporter.config().setCss("body { font-size: 14px; }");      // OPTIONAL custom CSS

        extent = new ExtentReports();
        extent.attachReporter(reporter);

        // ---------- System Information ----------
        extent.setSystemInfo("Tester", "Rahul");
        extent.setSystemInfo("Environment", System.getProperty("env", "QA"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));

        return extent;
    }
}
