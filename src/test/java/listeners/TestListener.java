package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest; // <-- Change to your Base class package if needed

public class TestListener extends BaseTest implements ITestListener {

    ExtentReports extent = ExtentReporterNG.getReporterObject();
    ExtentTest test;

    // Thread safety (parallel execution support)
    ThreadLocal<ExtentTest> threadLocalTest = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        threadLocalTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        threadLocalTest.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        threadLocalTest.get().fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();   // 🔥🔥 Without this report will NOT generate
    }
}
