package de.avensio.common.spring.config;

import de.avensio.common.spring.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * This class is a 100% Java replacement for the web.xml configuration
 * See <a href="https://www.baeldung.com/spring-xml-vs-java-config">Web.xml vs. Initializer</a>
 */
@Configuration
public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("de.avensio.common.spring.config");

        container.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));

        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        ApplicationContext.setWebApplicationContext(context);
    }
}
