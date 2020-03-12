import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RunWith(ParallelParameterized.class)
public class TestBase {

    protected static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    public MutableCapabilities capabilities;

    @Parameterized.Parameters
    public static MutableCapabilities[] getBrowserCapabilities() {
        return new MutableCapabilities[]{
                new ChromeOptions(),
                new FirefoxOptions()
        };
    }

    public TestBase(MutableCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Before
    public void setUp() throws Exception {
        RemoteWebDriver webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
        webDriver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        driver.set(webDriver);
    }

    @After
    public void tearDown() {
        getDriver().quit();
    }

    @AfterClass
    public static void remove() {
        driver.remove();
    }

}
