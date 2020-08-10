package de.avensio.common.spring.context;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Arrays;

@Component
@TestComponent
public class ApplicationContext implements ApplicationContextAware, ResourceLoaderAware, EnvironmentAware, ServletContextAware {
    public static org.springframework.context.ApplicationContext applicationContext;
    public static ServletContext servletContext;
    public static ResourceLoader resourceLoader;
    public static Environment environment;

    public static JdbcTemplate jdbcTemplate;
    public static RestTemplate restTemplate;
    public static JndiTemplate jndiTemplate;

    //public static TestEntityManager entityManager;
    public static WebApplicationContext webApplicationContext;
    public static int port;

    public static JndiTemplate getJndiTemplate() {
        return jndiTemplate;
    }


    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        de.avensio.common.spring.context.ApplicationContext.port = port;
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        de.avensio.common.spring.context.ApplicationContext.jdbcTemplate = jdbcTemplate;
    }

    public static RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public static void setRestTemplate(RestTemplate restTemplate) {
        de.avensio.common.spring.context.ApplicationContext.restTemplate = restTemplate;
    }

    public static WebApplicationContext getWebApplicationContext() {
        return webApplicationContext;
    }

    public static void setWebApplicationContext(WebApplicationContext webApplicationContext) { de.avensio.common.spring.context.ApplicationContext.webApplicationContext = webApplicationContext; }

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public static org.springframework.context.ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void logBeanNames(org.springframework.context.ApplicationContext applicationContext) {
        String[] beanDefinitionNames;
        if (applicationContext == null) {
            beanDefinitionNames = getApplicationContext().getBeanDefinitionNames();
        } else {
            beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        }
        Arrays.sort(beanDefinitionNames);
        System.out.println("");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Bean Names START ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for (String beanName : beanDefinitionNames) {
            System.out.println(beanName);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~ Bean Names END ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static void logBeanNames() {
        logBeanNames(null);
    }
}
