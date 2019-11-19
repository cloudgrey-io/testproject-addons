package io.cloudgrey.tp.addons.deeplink;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.cloudgrey.tp.addons.helpers.Helpers;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.IOSAction;
import io.testproject.java.sdk.v2.addons.helpers.IOSAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Action(name = "Open Deep Link")
public class OpenDeepLinkIOS implements IOSAction {

    @Parameter(description = "The deep link URL")
    public String url = "";

    @Parameter(description = "The bundle ID of the app to terminate before launching Safari (optional)")
    public String bundleId = "";

    private static final String SAFARI_BUNDLE_ID = "com.apple.mobilesafari";
    private static final By URL_BUTTON = MobileBy.iOSNsPredicateString(
        "type == 'XCUIElementTypeButton' && name CONTAINS 'URL'");
    private static final By URL_FIELD = MobileBy.iOSNsPredicateString(
        "type == 'XCUIElementTypeTextField' && name CONTAINS 'URL'");
    private static final By OPEN_BUTTON = MobileBy.iOSNsPredicateString(
        "type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");

    @Override
    public ExecutionResult execute(IOSAddonHelper helper) throws FailureException {
        Helpers.validateURL(url);
        IOSDriver driver = helper.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 15);
        if (!bundleId.equals("")) {
            driver.executeScript("mobile: terminateApp", ImmutableMap.of("bundleId", bundleId));
        }
        driver.executeScript("mobile: launchApp", ImmutableMap.of("bundleId", SAFARI_BUNDLE_ID));
        if (!driver.isKeyboardShown()) {
            wait.until(ExpectedConditions.presenceOfElementLocated(URL_BUTTON)).click();
        }
        driver.findElement(URL_FIELD).sendKeys(url.concat("\uE007"));
        wait.until(ExpectedConditions.presenceOfElementLocated(OPEN_BUTTON)).click();
        return ExecutionResult.PASSED;
    }
}
