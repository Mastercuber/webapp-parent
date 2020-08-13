package de.avensio.common.spring.config;

import de.avensio.common.CommonApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafView;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Configuration
@ComponentScan( basePackages = {"de.avensio.common.web"})
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/static/" };

    @Autowired
    private SpringTemplateEngine templateEngine;

    public WebMvcConfig() {
        super();
    }
    // configuration

    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {
        final Optional<HttpMessageConverter<?>> jsonConverterFound = converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).findFirst();
        if(jsonConverterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) jsonConverterFound.get();
            //converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            //converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("loginPage");
        registry.addViewController("/forgotPassword").setViewName("forgotPassword");

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
        registry.addResourceHandler("/product/img/**").addResourceLocations("file:" + CommonApplication.IMAGE_DIR);
    }

    class ThymeleafCustomView extends ThymeleafView {
        public ThymeleafCustomView(String name) {
            super(name);
            setLocale(Locale.GERMANY);
            setTemplateEngine(templateEngine);
        }
    }

    @Bean(name="content-part")
    @Scope("prototype")
    public ThymeleafView contentViewBean() {
        ThymeleafView view = new ThymeleafCustomView("layout");
        view.setMarkupSelector("#content");
        return view;
    }
}
