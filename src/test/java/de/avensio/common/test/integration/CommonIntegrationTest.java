package de.avensio.common.test.integration;

import de.avensio.common.persistence.model.security.Privilege;
import de.avensio.common.spring.context.ApplicationContext;
import de.avensio.common.test.base.IntegrationTestBase;
import de.avensio.common.util.Um;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.get;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;

@Log
public class CommonIntegrationTest extends IntegrationTestBase {
    private String ROLES_URI;
    private String PRIVILEGES_URI;

    @Before
    public void setUp() throws IOException {
        super.setUp();
        ROLES_URI = "/admin/roles";
        PRIVILEGES_URI = "/admin/privileges";
    }

    @Test
    public void restTemplateUsageTest() {
        RestTemplate restTemplate = ApplicationContext.getRestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("/login", String.class);
        log.info("~~~ Role count: " + forEntity.getBody().toString());
        Assert.assertNotNull(forEntity.getBody());
    }
    @Test
    public void restAssuredUsageTest() {
        createPrivilege();
        log.info("~~~ Privilege created!");
    }

    @Test
    public void testH2Connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String dbUrl = "jdbc:h2:mem:sa/sa;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE";
        Connection connection = DriverManager.getConnection(dbUrl, "sa", "sa");

        Assert.assertFalse(connection.isClosed());
        log.info("~~~ Database Meta Data: " + connection.getMetaData().toString());
        connection.close();
    }


    @Test
    public void securitySetupSuccessfullyDoneTest() {
        // Roles exist test
        given()
                .when()
                .get(ROLES_URI)
                .then()
                .statusCode(200)
                .body(containsString(Um.Roles.ROLE_ADMIN))
                .body(containsString(Um.Roles.ROLE_USER));

        if (debug) {
            log.info("~~~ Print roles: ");
            get(ROLES_URI).getBody().prettyPrint();
        }

        // Privilege exist test
        given()
                .when()
                .get(PRIVILEGES_URI)
                .then()
                .statusCode(200)
                .body(containsString(Um.Privileges.CAN_PRIVILEGE_READ))
                .body(containsString(Um.Privileges.CAN_PRIVILEGE_WRITE))
                .body(containsString(Um.Privileges.CAN_ROLE_READ))
                .body(containsString(Um.Privileges.CAN_ROLE_WRITE));

        if (debug) {
            log.info("~~~ Print privileges: ");
            get(PRIVILEGES_URI).getBody().prettyPrint();
        }
    }

    private void createPrivilege() {
        createPrivilege("CAN_SAVE");
    }

    private void createPrivilege(String privilegeName) {
        // create privilege
        given()
                .contentType("application/json")
                .body(new Privilege(privilegeName))
                .when()
                .post(PRIVILEGES_URI)
                .then()
                .statusCode(201);
    }
}
