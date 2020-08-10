package de.avensio.common.test.base;

import de.avensio.common.persistence.dao.jpa.security.IPrincipalJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IPrivilegeJpaDao;
import de.avensio.common.persistence.dao.jpa.security.IRoleJpaDao;
import de.avensio.common.service.security.IPrivilegeService;
import de.avensio.common.service.security.IRoleService;
import de.avensio.common.spring.context.ApplicationContext;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

@TestPropertySource(value = "classpath:application-integration-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
//@ContextConfiguration(classes = {IntegrationTestConfig.class, PersistenceJpaConfig.class, ServiceConfig.class}, loader = AnnotationConfigContextLoader.class)
//@WebAppConfiguration(value = "src/test/webapp")
public class IntegrationTestBase extends TestBase {
    @Autowired
    protected IRoleJpaDao roleJpaDao;
    @Autowired
    protected IPrivilegeJpaDao privilegeJpaDao;
    @Autowired
    protected IPrincipalJpaDao principalJpaDao;

    @Autowired
    protected IPrivilegeService privilegeService;
    @Autowired
    protected IRoleService roleService;

    protected RestTemplate restTemplate;
    @Autowired
    protected MockMvc mockMvc;

    private static boolean isDone = false;

    @BeforeClass
    public static void seUpBeforeClass() throws IOException {

    }

    @Before
    public void setUp() throws IOException {
        if (!isDone) {

            ArrayList<Filter> filters = new ArrayList<>();
            filters.add(new RequestLoggingFilter());
            filters.add(new ResponseLoggingFilter());
            filters.add(new ErrorLoggingFilter());
            RestAssured.filters(filters);
            ApplicationContext.setWebApplicationContext((WebApplicationContext) applicationContext);
            RestAssuredMockMvc.webAppContextSetup((WebApplicationContext) applicationContext);

            this.restTemplate = new RestTemplate(new MockMvcClientHttpRequestFactory(mockMvc));
            ApplicationContext.setRestTemplate(restTemplate);
            ApplicationContext.environment = env;
            //DebugUtil.logApplicationContext();
            //DebugUtil.logThread(Thread.currentThread());
            isDone = true;
        }
    }

    @AfterClass
    public static void tearDown() {
    }
}

