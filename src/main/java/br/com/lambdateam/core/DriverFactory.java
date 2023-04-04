package br.com.lambdateam.core;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static WebDriver driver;
    private DriverFactory() {}
    public static WebDriver getDriver() {
        if (driver == null) {
            switch (Properties.browser) {
                case FIREFOX: driver = new FirefoxDriver(); break;
                case CHROME: driver = new ChromeDriver(); break;
                case EDGE: driver = new EdgeDriver(); break;
            }
            driver.manage().window().setSize(new Dimension(1920,1080));
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
