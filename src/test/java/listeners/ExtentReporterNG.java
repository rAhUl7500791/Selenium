package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {

    static ExtentReports extent;

    public static ExtentReports getReporterObject() {

        // Report will be generated inside target folder
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/extent-report.html");

        // ---------- UI Settings ----------
        reporter.config().setTheme(Theme.DARK);
        reporter.config().setReportName("E2E Automation Report");
        reporter.config().setDocumentTitle("Test Execution Report");
        reporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");
        reporter.config().setEncoding("UTF-8");

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
