import io.appium.java_client.android.AndroidDriver;
import io.cloudgrey.tp.addons.deeplink.OpenDeepLinkAndroid;
import io.testproject.java.sdk.v2.Runner;
import java.io.IOException;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidDeepLinkTest {
    private static final String DEV_TOKEN = System.getenv("TESTPROJECT_DEV_KEY");
    private static final String DEVICE_UDID = "emulator-5554";
    private static final String PACKAGE_NAME = "io.cloudgrey.the_app";
    private static final String ACTIVITY_NAME = ".MainActivity";

    private static Runner runner;
    private static AndroidDriver driver;

    @BeforeAll
    public static void setup() throws InstantiationException {
        runner = Runner.createAndroid(DEV_TOKEN, DEVICE_UDID, PACKAGE_NAME, ACTIVITY_NAME);
        driver = runner.getDriver();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        runner.close();
    }

    @Before
    public void prepareApp() {
        driver.resetApp();
    }

    @Test
    public void testOpenDeepLinkAction() throws Exception {
        // Create Action
        OpenDeepLinkAndroid action = new OpenDeepLinkAndroid();
        action.url = "theapp://login/alice/mypassword";

        // Run action
        runner.run(action);

        // Verify
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@text, \"You are logged in as alice\")]")));
    }
}
