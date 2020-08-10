package de.avensio.common.test.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CommonIntegrationTest.class,
        PrivilegeControllerIntegrationTest.class,
        RoleControllerIntegrationTest.class
})
public class IntegrationTestSuit {
    //
}
