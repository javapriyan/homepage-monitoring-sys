package com.owler.util; /**
 * Created by karthikeyan on 9/9/16.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;

import static org.testng.AssertJUnit.assertFalse;
public class JSErrorsLogging {
    private WebDriver driver;

    @BeforeMethod
    public void setUp() throws IOException {
       DesiredCapabilities capabilities = DesiredCapabilities.chrome();
       LoggingPreferences loggingprefs = new LoggingPreferences();
       loggingprefs.enable(LogType.BROWSER, Level.ALL);
       capabilities.setCapability(CapabilityType.LOGGING_PREFS, loggingprefs);
       //System.setProperty("webdriver.chrome.driver","/home/kumaran/Documents/chromedriver");
        System.setProperty("webdriver.chrome.driver","C:\\Owler\\Resources\\chromedriver-win.exe");
       driver=new ChromeDriver(capabilities);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public String getJSLogs() {
        LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
        StringBuffer sb= new StringBuffer();
        for (LogEntry entry : logEntries) {
            String msg = new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage();
            //Report iff its a serious issue
            if(msg.contains("SEVERE")) {
                sb.append(msg + "\n");
            }
        }
        return sb.toString();
    }
    @Test
    public void testMethod() throws InterruptedException {
        //driver.get("https://codex.wordpress.org/Using_Your_Browser_to_Diagnose_JavaScript_Errors");
        driver.get("https://www.owler.com/");
        String logs = getJSLogs();
        assertFalse("Found some console error in the website"+logs,logs.length()>0);
    }
}