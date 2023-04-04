package br.com.lambdateam.core;

import org.junit.After;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static br.com.lambdateam.core.DriverFactory.getDriver;
import static br.com.lambdateam.core.DriverFactory.quitDriver;

public class BaseTest {

    @Rule
    public TestName testName = new TestName();

    @After
    public void endTest() throws IOException {
        TakesScreenshot ss = (TakesScreenshot) getDriver();
        File file = ss.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file, new File("target" + File.separator + "screenshot" +
                File.separator + testName.getMethodName() + ".jpg"));

        if(Properties.QUIT_BROWSER) {
            quitDriver();
        }
    }
}
