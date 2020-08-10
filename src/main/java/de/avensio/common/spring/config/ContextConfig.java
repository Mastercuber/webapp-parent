package de.avensio.common.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan( basePackages = {"de.avensio.common"})
public class ContextConfig {
    /**
     * Bean must be explicitly defined to get the property resolution mechanism working
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
         return new PropertySourcesPlaceholderConfigurer();
    }
}
