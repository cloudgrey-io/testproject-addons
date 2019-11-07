package io.cloudgrey.tp.addons.deeplink;

import com.google.common.collect.ImmutableMap;
import io.appium.java_client.android.AndroidDriver;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.annotations.v2.Parameter;
import io.testproject.java.sdk.v2.addons.AndroidAction;
import io.testproject.java.sdk.v2.addons.helpers.AndroidAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(name = "Open Deep Link")
public class OpenDeepLinkAndroid implements AndroidAction {

    @Parameter(description = "The deep link URL")
    public String url = "";

    @Parameter(description = "The package of the app for the URL (optional, will default to the currently-running app)")
    public String pkg = "";

    @Override
    public ExecutionResult execute(AndroidAddonHelper helper) throws FailureException {
        AndroidDriver driver = helper.getDriver();
        if (pkg.equals("")) {
            pkg = driver.getCurrentPackage();
        }
        driver.terminateApp(pkg);
        // TODO validate url
        driver.executeScript("mobile: deepLink", ImmutableMap.of(
            "url", url,
            "package", pkg
        ));
        return ExecutionResult.PASSED;
    }
}
