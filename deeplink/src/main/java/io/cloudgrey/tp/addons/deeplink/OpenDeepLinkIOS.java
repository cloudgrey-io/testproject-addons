package io.cloudgrey.tp.addons.deeplink;

import io.appium.java_client.ios.IOSDriver;
import io.testproject.java.annotations.v2.Action;
import io.testproject.java.sdk.v2.addons.IOSAction;
import io.testproject.java.sdk.v2.addons.helpers.IOSAddonHelper;
import io.testproject.java.sdk.v2.enums.ExecutionResult;
import io.testproject.java.sdk.v2.exceptions.FailureException;

@Action(name = "Open Deep Link")
public class OpenDeepLinkIOS implements IOSAction {

    @Override
    public ExecutionResult execute(IOSAddonHelper helper) throws FailureException {
        IOSDriver driver = helper.getDriver();
        return ExecutionResult.PASSED;
    }
}
