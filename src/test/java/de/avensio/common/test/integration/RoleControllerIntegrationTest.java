package de.avensio.common.test.integration;

import de.avensio.common.persistence.model.security.Privilege;
import de.avensio.common.persistence.model.security.Role;
import de.avensio.common.test.base.IntegrationTestBase;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@Log
public class RoleControllerIntegrationTest extends IntegrationTestBase {
    private HashSet<Privilege> privilegesSet;
    private String ROLES_URI;
    private String PRIVILEGES_URI;
    private static boolean isDone = false;

    @Before
    public void setUp() throws IOException {
        super.setUp();
        ROLES_URI = "/admin/roles";
        PRIVILEGES_URI = "/admin/privileges";

        if (!isDone) {
            createPrivilege("CAN_READ");
            createPrivilege("CAN_WRITE");
            isDone = true;
        }

        log.info("~~~ Before ends!");
    }

    @Test
    public void createRoleTest() {
        Integer count = get("/admin/roles/count").getBody().as(Integer.class);

        createRole();

        Assert.assertTrue(get("/admin/roles/count").getBody().as(Integer.class).intValue() == (count.intValue() + 1));
        log.info("~~~ Created role! ~~~~~~~~~~~");
    }

    private void createRole() {
        createRole("ADMIN");
    }

    private void createRole(String roleName) {
        Role role = new Role(roleName, privilegesSet);

        // create role
        given()
            .contentType("application/json")
            .body(role)
        .when()
            .post(ROLES_URI)
        .then()
            .statusCode(201);
    }

    private void createPrivilege(String name) {
        Privilege privilege = new Privilege(name);
        given()
                .body(privilege)
                .contentType("application/json")
                .when()
                .post(PRIVILEGES_URI)
                .then()
                .statusCode(201);
    }
}
