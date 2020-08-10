package de.avensio.common.test.integration;

import de.avensio.common.persistence.model.security.Privilege;
import de.avensio.common.test.base.IntegrationTestBase;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.extern.java.Log;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;

@Log
public class PrivilegeControllerIntegrationTest extends IntegrationTestBase {
    private String ROLES_URI;
    private String PRIVILEGES_URI;
    private static boolean isDone = false;

    @Before
    public void setUp() throws IOException {
        super.setUp();
        ROLES_URI = "/admin/roles";
        PRIVILEGES_URI = "/admin/privileges";

        if (!isDone) {
            // Revert database operations did before
            RestAssuredMockMvc.webAppContextSetup((WebApplicationContext) applicationContext);
            isDone = true;
        }
    }

    @Test
    public void createPrivilegeTest() {
        createPrivilege();
        log.info("~~~ Privilege created!");
        if (debug) {
            get(PRIVILEGES_URI).getBody().prettyPrint();
        }
    }

    private void createPrivilege() {
        createPrivilege("CAN_DELETE");
    }

    private void createPrivilege(String privilegeName) {
        Privilege privilege = new Privilege(privilegeName);

        // create role
        given()
            .contentType("application/json")
            .body(privilege)
        .when()
            .post(PRIVILEGES_URI)
        .then()
            .statusCode(201);
    }
}
