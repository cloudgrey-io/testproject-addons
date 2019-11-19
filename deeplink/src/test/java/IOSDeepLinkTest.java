import io.cloudgrey.tp.addons.deeplink.OpenDeepLinkIOS;
import io.testproject.java.sdk.v2.Runner;
import io.testproject.java.sdk.v2.drivers.IOSDriver;
import java.io.IOException;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class IOSDeepLinkTest {
    private static final String DEV_TOKEN = System.getenv("TESTPROJECT_DEV_KEY");
    private static final String DEVICE_UDID = System.getenv("IOS_DEVICE_ID");
    private static final String DEVICE_NAME = System.getenv("IOS_DEVICE_NAME");
    private static final String BUNDLE_ID = "io.cloudgrey.the-app";

    private static Runner runner;
    private static IOSDriver driver;

    @BeforeAll
    static void setup() throws InstantiationException {
        runner = Runner.createIOS(DEV_TOKEN, DEVICE_UDID, DEVICE_NAME, BUNDLE_ID);
        driver = runner.getDriver();
    }

    @AfterAll
    static void tearDown() throws IOException {
        driver.quit();
        runner.close();
    }

    @Before
    void prepareApp() {
        driver.resetApp();
    }

    @Test
    void testOpenDeepLinkAction() throws Exception {
        // Create Action
        OpenDeepLinkIOS action = new OpenDeepLinkIOS();
        action.url = "theapp://login/alice/mypassword";

        // Run action
        runner.run(action);

        // Verify
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@label, \"You are logged in as alice\")]")));
    }
}
