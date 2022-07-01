package com.github.a_oleg;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

public class TestLauncher {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = new LauncherDiscoveryRequestBuilder
                .request()
                .selectors();
        launcher.execute(request);
    }
}
